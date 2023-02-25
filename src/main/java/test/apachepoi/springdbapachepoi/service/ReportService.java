package test.apachepoi.springdbapachepoi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.apachepoi.springdbapachepoi.utils.ExcelGenerator;
import test.apachepoi.springdbapachepoi.exceptions.CustomException;
import test.apachepoi.springdbapachepoi.model.Client;
import test.apachepoi.springdbapachepoi.model.ReportWithClients;
import test.apachepoi.springdbapachepoi.model.ReportData;
import test.apachepoi.springdbapachepoi.repository.ClientRepository;
import test.apachepoi.springdbapachepoi.repository.ReportRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ClientRepository clientRepository;
    private final ReportRepository reportRepository;

    public void createReport() {
        List<Client> clients = clientRepository.findAll();
        List<String> jsonList = clients.stream().map(client -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.ofNullable(client)
                    .map(c -> {
                        try {
                            return objectMapper.writeValueAsString(c);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .orElse("");
        }).toList();

        String reportJson = "[" + String.join(",", jsonList) + "]";
        ReportWithClients reportWithClients = new ReportWithClients();
        reportWithClients.setReport(reportJson);
        reportRepository.save(reportWithClients);
    }

    public byte[] generateExcelReport(Long id) {
        Optional<ReportWithClients> reportOptional = reportRepository.findById(id);

        if (reportOptional.isEmpty()) {
            throw new CustomException("Report not found for id: " + id);
        }

        ReportWithClients reportWithClients = reportOptional.get();

        try {
            JsonNode reportData = new ObjectMapper().readTree(reportWithClients.getReport());
            if (reportData.isArray()) {
                List<ReportData> reportDataList = new ArrayList<>();
                for (JsonNode data : reportData) {
                    reportDataList.add(new ReportData(data));
                }
                return ExcelGenerator.generateExcelReport(reportDataList);
            } else {
                ReportData singleReportData = new ReportData(reportData);
                return ExcelGenerator.generateExcelReport(Collections.singletonList(singleReportData));
            }
        } catch (JsonProcessingException e) {
            throw new CustomException("Error generating Excel report: " + e.getMessage());
        }
    }

}

