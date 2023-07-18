<div align="center">


# Segundo Parcial Programacion II Mock 2021
### Tomás Bordaberry

</div>


- Object oriented programing midterm for programación II course in Universidad de Montevideo.
- Exam consisted of building an java app to manage utility bills.

***

## UML Diagram

***

## Methods

1.`public void agregarFactura(String nombreServicio, Long cedulaCliente, String concepto, LocalDate vencimiento, Long importe) throws InformacionInvalida, EntidadYaExiste`:
  - This method adds a new invoice to the system for the specified service and client. It checks if all the required fields are provided and valid. If the service or client does not exist, it creates them within this operation. If the invoice already exists for either the service or the client, it throws an `EntidadYaExiste` exception.
  
2.`public MyList<Factura> obtenerFacturasNoPagas(Long cedulaCliente) throws EntidadNoExiste, InformacionInvalida`:
  - This method retrieves the list of unpaid invoices for a given client. It checks if the client exists in the system and throws an `EntidadNoExiste` exception if the client does not exist. It also throws an `InformacionInvalida` exception if the client ID is not provided.
  
3.`public void pagarFactura(String nombreServicio, Long numeroFactura, Long cedulaCiente, LocalDate fechaPago) throws InformacionInvalida, EntidadNoExiste`:
  - This method allows payment of a specific invoice for a client. It checks if all the required fields are provided and valid. If the payment date is not provided, it uses the current date. It also checks if the service and client exist in the system. If the invoice does not exist for the client, it throws an `EntidadNoExiste` exception. If the invoice is already marked as paid, it throws an `InformacionInvalida` exception.
  
4.`public MyList<Cliente> obtenerClientesConDeudaVencida()`:
  - This method retrieves a list of clients who have unpaid invoices that are past their due date. It iterates through all the clients and checks if any of them have overdue invoices. The list is ordered by the client's ID in ascending order.
  
5. `public MyList<Servicio> obtenerRankingServiciosPagosVencidos()`:
  - This method retrieves a list of services with the highest occurrence of late payments. It iterates through all the services and creates a ranking based on the number of invoices paid past their due dates. The list is ordered in descending order, with the service with the highest occurrence of late payments at the top.


***
</br>

### Auxiliar Methods

1. `private Cliente buscarOCrearCliente(Long cedulaCliente)`:
  - This private helper method is used to search for a client in the binary search tree (`clientes`) based on the client's ID. If the client exists, it returns the client object. If the client does not exist, it creates a new client object, adds it to the binary search tree, and returns the new client object.

2. `private Servicio buscarOCrearServicio(String nombreServicio)`:
  - This private helper method is used to search for a service in the hash map (`servicios`) based on the service name. If the service exists, it returns the service object. If the service does not exist, it creates a new service object, adds it to the hash map, and returns the new service object.

3. `public MySearchBinaryTree<Long, Cliente> getClientes()`:
  - This getter method returns the binary search tree (`clientes`) containing the clients.

4. `public MyHash<String, Servicio> getServicios()`:
  - This getter method returns the hash map (`servicios`) containing the services.

***
</br>

## Entities

### `Cliente` class
- Represents a client entity and includes the following methods:

- Constructors:
  - `public Cliente()`: Default constructor.
  - `public Cliente(long cedula)`: Constructor that initializes the `cedula` (ID) of the client and creates an empty `MyHash` to store the client's invoices.
  - `public Cliente(long cedula, MyHash<Long,Factura> facturas)`: Constructor that initializes the `cedula` (ID) of the client and assigns the provided `facturas` hash map.

- Getters and Setters:
  - `public long getCedula()`: Retrieves the ID of the client.
  - `public void setCedula(long cedula)`: Sets the ID of the client.
  - `public MyHash<Long, Factura> getFacturas()`: Retrieves the hash map of invoices associated with the client.
  - `public void setFacturas(MyHash<Long, Factura> facturas)`: Sets the hash map of invoices associated with the client.

- Equals and HashCode:
  - `@Override public boolean equals(Object o)`: Overrides the `equals` method to compare clients based on their ID (`cedula`).
  - `@Override public int hashCode()`: Overrides the `hashCode` method to generate a hash code based on the ID (`cedula`).

- Additional Methods:
  - `public void agregarFactura(Factura factura) throws EntidadYaExiste`: Adds a new invoice (`Factura`) to the client's invoice hash map (`facturas`). If the invoice with the same number already exists, it throws an `EntidadYaExiste` exception.
  - `@Override public int compareTo(Cliente o)`: Implements the `compareTo` method from the `Comparable` interface to compare clients based on their ID (`cedula`).
  - `public MyList<Factura> facturasList()`: Retrieves a `MyList` of all invoices associated with the client.
  - `public boolean tieneFactura(Long numeroFactura)`: Checks if the client has an invoice with the provided invoice number (`numeroFactura`).
  - `public MyList<Factura> facturasNoPagas()`: Retrieves a `MyList` of unpaid invoices for the client.
  - `public boolean deudaVencida()`: Checks if the client has any overdue unpaid invoices and returns `true` if there is at least one overdue unpaid invoice.

***
</br>

### `Servicio` class
- Represents a service entity and includes the following methods:

- Constructors:
  - `public Servicio()`: Default constructor.
  - `public Servicio(String nombre, MyHash<Long, Factura> facturas)`: Constructor that initializes the service with the provided name and the existing `facturas` hash map.
  - `public Servicio(String nombre)`: Constructor that initializes the service with the provided name and creates an empty `MyHash` to store the service's invoices.

- Getters and Setters:
  - `public String getNombre()`: Retrieves the name of the service.
  - `public void setNombre(String nombre)`: Sets the name of the service.
  - `public MyHash<Long, Factura> getFacturas()`: Retrieves the hash map of invoices associated with the service.
  - `public void setFacturas(MyHash<Long, Factura> facturas)`: Sets the hash map of invoices associated with the service.

- Additional Methods:
  - `public void agregarFactura(Factura factura) throws EntidadYaExiste`: Adds a new invoice (`Factura`) to the service's invoice hash map (`facturas`). If the invoice with the same number already exists, it throws an `EntidadYaExiste` exception.
  - `@Override public boolean equals(Object o)`: Overrides the `equals` method to compare services based on their names (case-insensitive).
  - `@Override public int hashCode()`: Overrides the `hashCode` method to generate a hash code based on the name of the service.
  - `public MyList<Factura> facturasList()`: Retrieves a `MyList` of all invoices associated with the service.
  - `public boolean tieneFactura(Long numeroFactura)`: Checks if the service has an invoice with the provided invoice number (`numeroFactura`).
  - `public int cantidadDeudaVencidas()`: Counts the number of invoices associated with the service that have overdue payments.
  - `@Override public int compareTo(Servicio o)`: Implements the `compareTo` method from the `Comparable` interface to compare services based on the number of invoices with overdue payments (`cantidadDeudaVencidas`).

***
</br>

### `Factura` class
- Represents an invoice and includes the following methods:

-  Constructors:
  - `public Factura()`: Default constructor that generates a unique invoice number (`numeroFactura`) using the `getNextNumeroFactura` method.
  - `public Factura(String concepto, LocalDate vencimiento, long importe, Cliente cliente, Servicio servicio)`: Constructor that initializes the invoice with the provided concept, due date, amount, client, and service. It also generates a unique invoice number using the `getNextNumeroFactura` method.

- Getters and Setters:
  - Various getters and setters for the invoice properties such as `numeroFactura`, `concepto`, `vencimiento`, `importe`, `fechaPago`, `estaPaga`, `cliente`, and `servicio`.

- Additional Methods:
  - `private static synchronized long getNextNumeroFactura()`: Generates the next unique invoice number using a static variable `proximoNumeroFactura` and increments it for subsequent invoices.
  - `public static void setProximoNumeroFactura(long proximoNumeroFactura)`: Sets the value of the static variable `proximoNumeroFactura`.
  - `public void pagar(LocalDate fecha)`: Marks the invoice as paid by setting the payment date (`fechaPago`) to the provided date and setting `estaPaga` to `true`.
  - `@Override public boolean equals(Object o)`: Overrides the `equals` method to compare invoices based on their invoice numbers.
  - `@Override public int hashCode()`: Overrides the `hashCode` method to generate a hash code based on the invoice number.
  - `public long cedulaCliente()`: Retrieves the client's identification number associated with the invoice.
  - `public String nombreServicio()`: Retrieves the name of the service associated with the invoice.
  - `public boolean esVencida()`: Checks if the invoice is overdue based on the current date (`LocalDate.now()`).
  - `public boolean pagoVencido()`: Checks if the invoice payment was made after the due date, indicating a late payment.
