# Expense Reimbursement System (ERS)

## Project Description

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can log in and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

* Apache Tomcat - version 9.0.41
* Bootstrap - version 4.6.0
* Java Servlet API - version 4.0.1
* Jackson (jackson-databind) - version 2.12.0
* Junit (junit-jupiter-api) - version 5.7.0
* Log4j 2.x (log4j-core) - version 2.14.0
* Mockito (mockito-core) - version 3.6.28
* Postgresql JDBC driver - version 42.2.18

## Features

* Employees can register new accounts with the system.
* Employees can submit new reimbursement tickets and view their ticket submission history, including approval status.
* Financial managers can view the entire ticket submission history, by approval status, and either approve or deny tickets which have not yet reviewed.

## Getting Started

### Build dependencies

* Apache Maven 3.6.3 or later
* JDK 8 or later

### Runtime dependencies

* Apache Tomcat 9

### Build

```
$ git clone https://github.com/elishaSword/employee-reimbursement-system.git
$ cd employee-reimbursement-system
$ mvn package
```

### Install

The resulting `.war` will be built in `target`, and can be manually deployed to an Apache Tomcat instance configured to allow access to its manager interface. The resulting application will be available at `/Project1-0.0.1-SHAPSHOT/` on the server.

## License

This software is intellectual property of Revaure LLC. Unauthorized reproduction is strictly prohibited.

