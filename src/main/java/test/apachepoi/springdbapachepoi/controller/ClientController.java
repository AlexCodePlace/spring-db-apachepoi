package test.apachepoi.springdbapachepoi.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import test.apachepoi.springdbapachepoi.model.Client;
import test.apachepoi.springdbapachepoi.service.ClientService;
import test.apachepoi.springdbapachepoi.service.ReportService;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ReportService reportService;

    public ClientController(ClientService clientService, ReportService reportService) {
        this.clientService = clientService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody Client client) {
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingClient.setFirstName(client.getFirstName());
        existingClient.setLastName(client.getLastName());
        existingClient.setTown(client.getTown());
        existingClient.setAge(client.getAge());
        existingClient.setBalance(client.getBalance());
        clientService.saveClient(existingClient);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Client existingClient = clientService.getClientById(id);
        if (existingClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/createreport")
    public ResponseEntity<String> createReport() {
        reportService.createReport();
        return ResponseEntity.ok("Report created");
    }

    @GetMapping("/takereport/{id}")
    public ResponseEntity<byte[]> getReportExcel(@PathVariable Long id) {
        byte[] bytes = reportService.generateExcelReport(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}
