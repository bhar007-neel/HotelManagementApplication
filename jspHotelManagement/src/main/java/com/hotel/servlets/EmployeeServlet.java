package com.hotel.servlets;

import java.io.IOException;
import java.util.List;

import com.hotel.dao.EmployeeDAO;
import com.hotel.model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee") // Maps servlet to employee
public class EmployeeServlet extends HttpServlet {
//    private static final String URL = "jdbc:postgresql://localhost:5432/HotelManagement";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "";


    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listEmployees(request, response);
                break;
            case "get":
                getEmployeeById(request, response);
                break;
            case "searchBySSN":
                searchBySSN(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }

        switch (action) {
            case "add":
                addEmployee(request, response);
                break;
            case "update":
                updateEmployee(request, response);
                break;
            case "delete":
                deleteEmployee(request, response);
                break;
            case "searchBySSN":
                searchBySSN(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
    private void searchBySSN(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ssn = request.getParameter("ssn");
        System.out.println("Searching for SSN: " + ssn);

        Employee employee = employeeDAO.getEmployeeBySSN(ssn);

        if (employee != null) {
            System.out.println("Employee found: " + employee.getName());
            request.setAttribute("employee", employee);
        } else {
            System.out.println("No employee found with SSN: " + ssn);
            request.setAttribute("error", "No employee found with SSN: " + ssn);
        }

        request.getRequestDispatcher("employee-edit.jsp").forward(request, response);
    }



    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }

    private void getEmployeeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            Employee employee = employeeDAO.getEmployeeById(employeeId);

            if (employee != null) {
                request.setAttribute("employee", employee);
                request.getRequestDispatcher("employee-details.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee ID");
        }
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int hotelId = Integer.parseInt(request.getParameter("hotelId"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String ssn = request.getParameter("ssn");
            String role = request.getParameter("role");

            Employee newEmployee = new Employee(0, name, address, ssn, role);
            employeeDAO.addEmployee(newEmployee, hotelId);
            response.sendRedirect("employee-edit.jsp");


        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format");
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String ssn = request.getParameter("ssn");
            String role = request.getParameter("role");

            Employee updatedEmployee = new Employee(employeeId, name, address, ssn, role);
            employeeDAO.updateEmployee(updatedEmployee);
            response.sendRedirect("employee-edit.jsp");


        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format");
        }
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            employeeDAO.deleteEmployee(employeeId);
            response.sendRedirect("employee-edit.jsp");


        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee ID");
        }
    }
}
