package com.itech.employeeDemo;

import com.itech.employeeDemo.model.Employee;
import com.itech.employeeDemo.model.PersonalInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EmployeeController {
    List<Employee> employeeList=new ArrayList<>();
    @RequestMapping(value ="/employee/all")
    public List<Employee> getEmployees(){
        List<Employee> employees = generateData();
        return employeeList;
    }
    @RequestMapping(value = "/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Integer id){
        List<Employee>employeeList1=generateData();
        /*for(Employee employee:employeeList1){
            if(employee.getEmpId()==id){
                ResponseEntity responseEntity = new ResponseEntity(employee, HttpStatus.OK);
                return responseEntity;
            }
        }*/
        Optional<Employee>emp=employeeList1.stream().filter(i->i.getEmpId()==id).findFirst();
        if(emp.isPresent()) {
            ResponseEntity responseEntity = new ResponseEntity(emp, HttpStatus.OK);
            return responseEntity;
        }
        Map<String,String>map = new HashMap<>();
        map.put("Message","Employee not found");
        ResponseEntity responseEntity = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        return responseEntity;

    }
    @RequestMapping(value = "/employee/add",method = RequestMethod.POST)
    public ResponseEntity createEmployee(@RequestBody Employee employee){
       boolean b= addEmployee(employee);
        Map<String,String>map = new HashMap<>();
        ResponseEntity responseEntity;
        if(b==true) {
            map.put("Message", "Employee added");
            responseEntity = new ResponseEntity(map, HttpStatus.CREATED);
            return responseEntity;
        }
        else
        {
            map.put("Message", "Already Exists");
            responseEntity = new ResponseEntity(map, HttpStatus.ALREADY_REPORTED);
            return responseEntity;
        }
    }
    @RequestMapping(value = "/employee/update", method = RequestMethod.PUT)
    public ResponseEntity updateEmployee(@RequestBody Employee employee) {
        boolean updated = updateExistingEmployee(employee);
        Map<String, String> map = new HashMap<>();
        ResponseEntity responseEntity;

        if(updated) {
            map.put("message", "Employee updated");
            responseEntity = new ResponseEntity(map, HttpStatus.OK);
        } else {
            map.put("message", "Employee not found");
            responseEntity = new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
    private List<Employee>generateData(){
        if(employeeList.isEmpty()){
            Employee e1 = new Employee();
            e1.setEmpId(111);
            e1.setEmpName("Swetha");
            e1.setSalary(10000f);
            PersonalInfo personalInfo1 = new PersonalInfo();
            personalInfo1.setAge(22);
            personalInfo1.setBloodGroup("A+ve");
            personalInfo1.setEmail("swetha@gmail.com");
            e1.setPersonalInfo(personalInfo1);
            Employee e2 = new Employee();
            e2.setEmpId(222);
            e2.setEmpName("Pikachu");
            e2.setSalary(20000f);
            PersonalInfo personalInfo2 = new PersonalInfo();
            personalInfo2.setAge(22);
            personalInfo2.setBloodGroup("O+ve");
            personalInfo2.setEmail("pikachu@gmail.com");
            e2.setPersonalInfo(personalInfo2);
            employeeList.add(e1);
            employeeList.add(e2);
        }
        return employeeList;
    }
    private boolean addEmployee(Employee employee){
        List<Employee>employees=generateData();
        Map <String,String>map = new HashMap<>();
       Optional<Employee>e= employees.stream().filter(i->i.getEmpId()==employee.getEmpId()).findAny();
        if(!e.isPresent()) {
            employees.add(employee);
            return true;
        }
        else
            return false;

    }
    private boolean updateExistingEmployee(Employee employee){
        List<Employee> employees = generateData();
        Iterator<Employee> iterator = employees.iterator();
        boolean removed = false;

        while(iterator.hasNext()) {
            Employee employee1 = iterator.next();
            if(employee1.getEmpId() ==employee.getEmpId()) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if(removed) {
            employees.add(employee);
        }
        return removed;
    }

}
