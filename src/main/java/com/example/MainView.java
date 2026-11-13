package com.example;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import com.example.model.Administrador;
import com.example.model.Apartamento;
import com.example.model.CuartoUtil;
import com.example.model.Parqueadero;
import com.example.model.Propiedad;
import com.example.model.Propietario;
import com.example.model.Residente;
import com.example.model.Usuario;
import com.example.service.CrudService;
import com.example.service.ExportService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Gestión de propiedad horizontal")
@Route("")
public class MainView extends VerticalLayout {

    private final CrudService crudService;
    private final ExportService exportService;

    public MainView(CrudService crudService, ExportService exportService) {
        this.crudService = crudService;
        this.exportService = exportService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TabSheet tabs = new TabSheet();
        tabs.setWidthFull();
        tabs.add("Administradores", crearSeccionAdministradores());
        tabs.add("Apartamentos", crearSeccionApartamentos());
        tabs.add("Cuartos útiles", crearSeccionCuartosUtiles());
        tabs.add("Parqueaderos", crearSeccionParqueaderos());
        tabs.add("Propiedades", crearSeccionPropiedades());
        tabs.add("Propietarios", crearSeccionPropietarios());
        tabs.add("Residentes", crearSeccionResidentes());
        tabs.add("Usuarios", crearSeccionUsuarios());

        add(new H2("Gestión de propiedad horizontal"), tabs);
    }

    private Component crearSeccionAdministradores() {
        VerticalLayout layout = new VerticalLayout();
        TextField documento = new TextField("Documento");
        TextField nombre = new TextField("Nombre");
        TextField apellidos = new TextField("Apellidos");
        DatePicker fechaNacimiento = new DatePicker("Fecha de nacimiento");
        TextField celular = new TextField("Celular");
        TextField correo = new TextField("Correo");
        TextField nit = new TextField("NIT de la propiedad");
        FormLayout form = new FormLayout(documento, nombre, apellidos, fechaNacimiento, celular, correo, nit);

        Grid<Administrador> grid = new Grid<>(Administrador.class, false);
        grid.addColumn(Administrador::getDocumento).setHeader("Documento").setAutoWidth(true);
        grid.addColumn(Administrador::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Administrador::getApellidos).setHeader("Apellidos").setAutoWidth(true);
        grid.addColumn(Administrador::getFechaDeNacimiento).setHeader("Fecha de nacimiento").setAutoWidth(true);
        grid.addColumn(Administrador::getCelular).setHeader("Celular").setAutoWidth(true);
        grid.addColumn(Administrador::getCorreo).setHeader("Correo").setAutoWidth(true);
        grid.addColumn(Administrador::getNit).setHeader("NIT").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Administrador.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(administrador -> {
            documento.setValue(valor(administrador.getDocumento()));
            nombre.setValue(valor(administrador.getNombre()));
            apellidos.setValue(valor(administrador.getApellidos()));
            fechaNacimiento.setValue(administrador.getFechaDeNacimiento());
            celular.setValue(valor(administrador.getCelular()));
            correo.setValue(valor(administrador.getCorreo()));
            nit.setValue(valor(administrador.getNit()));
        }));

        Button crear = botonCrear(() -> {
            if (!validar(documento.getValue(), "El documento es obligatorio")
                    || !validar(fechaNacimiento.getValue(), "Seleccione la fecha de nacimiento")) {
                return;
            }
            Administrador administrador = new Administrador(documento.getValue().trim(), nombre.getValue(),
                    apellidos.getValue(), fechaNacimiento.getValue(), celular.getValue(), correo.getValue(),
                    nit.getValue());
            crudService.guardar(administrador);
            refrescar.run();
            Notification.show("Administrador guardado");
        });
        Button consultar = new Button("Consultar", event -> {
            String id = documento.getValue().trim();
            if (!validar(id, "Ingrese el documento que desea consultar")) {
                return;
            }
            Administrador administrador = crudService.buscarPorId(Administrador.class, id);
            if (administrador == null) {
                Notification.show("No se encontró el administrador");
            } else {
                grid.select(administrador);
            }
        });
        Button actualizar = new Button("Actualizar", event -> {
            String id = documento.getValue().trim();
            if (!validar(id, "Ingrese el documento del administrador")
                    || !validar(fechaNacimiento.getValue(), "Seleccione la fecha de nacimiento")) {
                return;
            }
            Administrador administrador = crudService.buscarPorId(Administrador.class, id);
            if (administrador == null) {
                Notification.show("No se encontró el administrador");
                return;
            }
            administrador.setNombre(nombre.getValue());
            administrador.setApellidos(apellidos.getValue());
            administrador.setFechaDeNacimiento(fechaNacimiento.getValue());
            administrador.setCelular(celular.getValue());
            administrador.setCorreo(correo.getValue());
            administrador.setNit(nit.getValue());
            crudService.actualizar(administrador);
            refrescar.run();
            Notification.show("Administrador actualizado");
        });
        Button eliminar = new Button("Eliminar", event -> {
            String id = documento.getValue().trim();
            if (!validar(id, "Ingrese el documento del administrador")) {
                return;
            }
            if (crudService.eliminar(Administrador.class, id)) {
                refrescar.run();
                limpiar(documento, nombre, apellidos, celular, correo, nit);
                fechaNacimiento.clear();
                grid.deselectAll();
                Notification.show("Administrador eliminado");
            } else {
                Notification.show("No se encontró el administrador");
            }
        });
        eliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        layout.add(crearDescargas("Administradores", "administradores", Administrador.class,
                        List.of("Documento", "Nombre", "Apellidos", "Fecha de nacimiento", "Celular", "Correo", "NIT"),
                        administrador -> List.of(valor(administrador.getDocumento()), valor(administrador.getNombre()),
                                valor(administrador.getApellidos()), valor(administrador.getFechaDeNacimiento()),
                                valor(administrador.getCelular()), valor(administrador.getCorreo()),
                                valor(administrador.getNit()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                botonLimpiar(grid, documento, nombre, apellidos, celular, correo, nit, fechaNacimiento)), grid);
        return layout;
    }

    private Component crearSeccionApartamentos() {
        VerticalLayout layout = new VerticalLayout();
        TextField id = new TextField("ID del apartamento");
        id.setReadOnly(true);
        TextField numero = new TextField("Número");
        TextField torre = new TextField("Torre");
        TextField parqueadero = new TextField("Parqueadero");
        TextField cuartoUtil = new TextField("Cuarto útil");
        TextField documentoResidente = new TextField("Documento del residente");
        FormLayout form = new FormLayout(id, numero, torre, parqueadero, cuartoUtil, documentoResidente);

        Grid<Apartamento> grid = new Grid<>(Apartamento.class, false);
        grid.addColumn(Apartamento::getIdApto).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Apartamento::getNumeroApto).setHeader("Número").setAutoWidth(true);
        grid.addColumn(Apartamento::getTorre).setHeader("Torre").setAutoWidth(true);
        grid.addColumn(Apartamento::getParqueadero).setHeader("Parqueadero").setAutoWidth(true);
        grid.addColumn(Apartamento::getCuartoUtil).setHeader("Cuarto útil").setAutoWidth(true);
        grid.addColumn(Apartamento::getIdDocumento).setHeader("Documento del residente").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Apartamento.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(apartamento -> {
            id.setValue(valor(apartamento.getIdApto()));
            numero.setValue(valor(apartamento.getNumeroApto()));
            torre.setValue(valor(apartamento.getTorre()));
            parqueadero.setValue(valor(apartamento.getParqueadero()));
            cuartoUtil.setValue(valor(apartamento.getCuartoUtil()));
            documentoResidente.setValue(valor(apartamento.getIdDocumento()));
        }));

        Button crear = botonCrear(() -> {
            Apartamento apartamento = new Apartamento(null, numero.getValue(), torre.getValue(),
                    parqueadero.getValue(), cuartoUtil.getValue(), documentoResidente.getValue());
            crudService.guardar(apartamento);
            refrescar.run();
            id.setValue(valor(apartamento.getIdApto()));
            Notification.show("Apartamento guardado con ID: " + apartamento.getIdApto());
        });
        Button consultar = botonConsultar(grid, Apartamento.class, id, "Apartamento");
        Button actualizar = new Button("Actualizar", event -> {
            Long idValue = parseId(id);
            if (idValue == null) {
                return;
            }
            Apartamento apartamento = crudService.buscarPorId(Apartamento.class, idValue);
            if (apartamento == null) {
                Notification.show("No se encontró el apartamento");
                return;
            }
            apartamento.setNumeroApto(numero.getValue());
            apartamento.setTorre(torre.getValue());
            apartamento.setParqueadero(parqueadero.getValue());
            apartamento.setCuartoUtil(cuartoUtil.getValue());
            apartamento.setIdDocumento(documentoResidente.getValue());
            crudService.actualizar(apartamento);
            refrescar.run();
            Notification.show("Apartamento actualizado");
        });
        Button eliminar = botonEliminar(grid, Apartamento.class, id, "Apartamento",
                () -> limpiar(id, numero, torre, parqueadero, cuartoUtil, documentoResidente), refrescar);
        layout.add(crearDescargas("Apartamentos", "apartamentos", Apartamento.class,
                        List.of("ID", "Número", "Torre", "Parqueadero", "Cuarto útil", "Documento del residente"),
                        apartamento -> List.of(valor(apartamento.getIdApto()), valor(apartamento.getNumeroApto()),
                                valor(apartamento.getTorre()), valor(apartamento.getParqueadero()),
                                valor(apartamento.getCuartoUtil()), valor(apartamento.getIdDocumento()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                botonLimpiar(grid, id, numero, torre, parqueadero, cuartoUtil, documentoResidente)), grid);
        return layout;
    }

    private Component crearSeccionPropiedades() {
        VerticalLayout layout = new VerticalLayout();
        TextField nit = new TextField("NIT");
        nit.setReadOnly(true);
        TextField nombre = new TextField("Nombre de la propiedad");
        TextField direccion = new TextField("Dirección");
        TextField ciudad = new TextField("Ciudad");
        TextField departamento = new TextField("Departamento");
        TextField idApartamento = new TextField("ID del apartamento");
        FormLayout form = new FormLayout(nit, nombre, direccion, ciudad, departamento, idApartamento);

        Grid<Propiedad> grid = new Grid<>(Propiedad.class, false);
        grid.addColumn(Propiedad::getNit).setHeader("NIT").setAutoWidth(true);
        grid.addColumn(Propiedad::getNombrePropiedad).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Propiedad::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(Propiedad::getCiudad).setHeader("Ciudad").setAutoWidth(true);
        grid.addColumn(Propiedad::getDepartamento).setHeader("Departamento").setAutoWidth(true);
        grid.addColumn(Propiedad::getIdApto).setHeader("ID del apartamento").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Propiedad.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(propiedad -> {
            nit.setValue(valor(propiedad.getNit()));
            nombre.setValue(valor(propiedad.getNombrePropiedad()));
            direccion.setValue(valor(propiedad.getDireccion()));
            ciudad.setValue(valor(propiedad.getCiudad()));
            departamento.setValue(valor(propiedad.getDepartamento()));
            idApartamento.setValue(valor(propiedad.getIdApto()));
        }));

        Button crear = botonCrear(() -> {
            Propiedad propiedad = new Propiedad(null, nombre.getValue(), direccion.getValue(), ciudad.getValue(),
                    departamento.getValue(), idApartamento.getValue());
            crudService.guardar(propiedad);
            refrescar.run();
            nit.setValue(valor(propiedad.getNit()));
            Notification.show("Propiedad guardada con NIT: " + propiedad.getNit());
        });
        Button consultar = botonConsultar(grid, Propiedad.class, nit, "Propiedad");
        Button actualizar = new Button("Actualizar", event -> {
            Long idValue = parseId(nit);
            if (idValue == null) {
                return;
            }
            Propiedad propiedad = crudService.buscarPorId(Propiedad.class, idValue);
            if (propiedad == null) {
                Notification.show("No se encontró la propiedad");
                return;
            }
            propiedad.setNombrePropiedad(nombre.getValue());
            propiedad.setDireccion(direccion.getValue());
            propiedad.setCiudad(ciudad.getValue());
            propiedad.setDepartamento(departamento.getValue());
            propiedad.setIdApto(idApartamento.getValue());
            crudService.actualizar(propiedad);
            refrescar.run();
            Notification.show("Propiedad actualizada");
        });
        Button eliminar = botonEliminar(grid, Propiedad.class, nit, "Propiedad",
                () -> limpiar(nit, nombre, direccion, ciudad, departamento, idApartamento), refrescar);
        layout.add(crearDescargas("Propiedades", "propiedades", Propiedad.class,
                        List.of("NIT", "Nombre", "Dirección", "Ciudad", "Departamento", "ID del apartamento"),
                        propiedad -> List.of(valor(propiedad.getNit()), valor(propiedad.getNombrePropiedad()),
                                valor(propiedad.getDireccion()), valor(propiedad.getCiudad()),
                                valor(propiedad.getDepartamento()), valor(propiedad.getIdApto()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                botonLimpiar(grid, nit, nombre, direccion, ciudad, departamento, idApartamento)), grid);
        return layout;
    }

    private Component crearSeccionPropietarios() {
        VerticalLayout layout = new VerticalLayout();
        TextField id = new TextField("ID del propietario");
        id.setReadOnly(true);
        TextField nombre = new TextField("Nombre");
        TextField apellido = new TextField("Apellido");
        DatePicker fechaNacimiento = new DatePicker("Fecha de nacimiento");
        TextField celular = new TextField("Celular");
        TextField mail = new TextField("Correo");
        TextField reside = new TextField("¿Reside en la propiedad?");
        FormLayout form = new FormLayout(id, nombre, apellido, fechaNacimiento, celular, mail, reside);

        Grid<Propietario> grid = new Grid<>(Propietario.class, false);
        grid.addColumn(Propietario::getIdPropietario).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Propietario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Propietario::getApellido).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(Propietario::getFechaNacimiento).setHeader("Fecha de nacimiento").setAutoWidth(true);
        grid.addColumn(Propietario::getCelular).setHeader("Celular").setAutoWidth(true);
        grid.addColumn(Propietario::getMail).setHeader("Correo").setAutoWidth(true);
        grid.addColumn(Propietario::getReside).setHeader("Reside").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Propietario.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(propietario -> {
            id.setValue(valor(propietario.getIdPropietario()));
            nombre.setValue(valor(propietario.getNombre()));
            apellido.setValue(valor(propietario.getApellido()));
            fechaNacimiento.setValue(propietario.getFechaNacimiento());
            celular.setValue(valor(propietario.getCelular()));
            mail.setValue(valor(propietario.getMail()));
            reside.setValue(valor(propietario.getReside()));
        }));

        Button crear = botonCrear(() -> {
            if (!validar(fechaNacimiento.getValue(), "Seleccione la fecha de nacimiento")) {
                return;
            }
            Propietario propietario = new Propietario(null, nombre.getValue(), apellido.getValue(),
                    fechaNacimiento.getValue(), celular.getValue(), mail.getValue(), reside.getValue());
            crudService.guardar(propietario);
            refrescar.run();
            id.setValue(valor(propietario.getIdPropietario()));
            Notification.show("Propietario guardado con ID: " + propietario.getIdPropietario());
        });
        Button consultar = botonConsultar(grid, Propietario.class, id, "Propietario");
        Button actualizar = new Button("Actualizar", event -> {
            Long idValue = parseId(id);
            if (idValue == null || !validar(fechaNacimiento.getValue(), "Seleccione la fecha de nacimiento")) {
                return;
            }
            Propietario propietario = crudService.buscarPorId(Propietario.class, idValue);
            if (propietario == null) {
                Notification.show("No se encontró el propietario");
                return;
            }
            propietario.setNombre(nombre.getValue());
            propietario.setApellido(apellido.getValue());
            propietario.setFechaNacimiento(fechaNacimiento.getValue());
            propietario.setCelular(celular.getValue());
            propietario.setMail(mail.getValue());
            propietario.setReside(reside.getValue());
            crudService.actualizar(propietario);
            refrescar.run();
            Notification.show("Propietario actualizado");
        });
        Button eliminar = botonEliminar(grid, Propietario.class, id, "Propietario",
                () -> {
                    limpiar(id, nombre, apellido, celular, mail, reside);
                    fechaNacimiento.clear();
                }, refrescar);
        layout.add(crearDescargas("Propietarios", "propietarios", Propietario.class,
                        List.of("ID", "Nombre", "Apellido", "Fecha de nacimiento", "Celular", "Correo", "Reside"),
                        propietario -> List.of(valor(propietario.getIdPropietario()), valor(propietario.getNombre()),
                                valor(propietario.getApellido()), valor(propietario.getFechaNacimiento()),
                                valor(propietario.getCelular()), valor(propietario.getMail()),
                                valor(propietario.getReside()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                        botonLimpiar(grid, id, nombre, apellido, fechaNacimiento, celular, mail, reside)), grid);
        return layout;
    }

    private Component crearSeccionParqueaderos() {
        VerticalLayout layout = new VerticalLayout();
        TextField id = new TextField("ID del parqueadero");
        id.setRequiredIndicatorVisible(true);
        TextField tipo = new TextField("Tipo");
        TextField idPropietario = new TextField("ID del propietario");
        TextField idResidente = new TextField("ID del residente");
        TextField idApartamento = new TextField("ID del apartamento");
        FormLayout form = new FormLayout(id, tipo, idPropietario, idResidente, idApartamento);

        Grid<Parqueadero> grid = new Grid<>(Parqueadero.class, false);
        grid.addColumn(Parqueadero::getIdParqueadero).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Parqueadero::getTipo).setHeader("Tipo").setAutoWidth(true);
        grid.addColumn(Parqueadero::getIdPropietario).setHeader("ID del propietario").setAutoWidth(true);
        grid.addColumn(Parqueadero::getIdResidente).setHeader("ID del residente").setAutoWidth(true);
        grid.addColumn(Parqueadero::getIdApto).setHeader("ID del apartamento").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Parqueadero.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(parqueadero -> {
            id.setValue(valor(parqueadero.getIdParqueadero()));
            tipo.setValue(valor(parqueadero.getTipo()));
            idPropietario.setValue(valor(parqueadero.getIdPropietario()));
            idResidente.setValue(valor(parqueadero.getIdResidente()));
            idApartamento.setValue(valor(parqueadero.getIdApto()));
        }));

        Button crear = botonCrear(() -> {
            String idValue = id.getValue().trim();
            if (!validar(idValue, "Ingrese el ID del parqueadero")) {
                return;
            }
            Parqueadero parqueadero = new Parqueadero(idValue, tipo.getValue(), idPropietario.getValue(),
                    idResidente.getValue(), idApartamento.getValue());
            crudService.guardar(parqueadero);
            refrescar.run();
            id.setValue(valor(parqueadero.getIdParqueadero()));
            Notification.show("Parqueadero guardado con ID: " + parqueadero.getIdParqueadero());
        });
        Button consultar = new Button("Consultar", event -> {
            String idValue = id.getValue().trim();
            if (!validar(idValue, "Ingrese el ID del parqueadero que desea consultar")) {
                return;
            }
            Parqueadero parqueadero = crudService.buscarPorId(Parqueadero.class, idValue);
            if (parqueadero == null) {
                Notification.show("No se encontró el parqueadero");
            } else {
                grid.select(parqueadero);
            }
        });
        Button actualizar = new Button("Actualizar", event -> {
            String idValue = id.getValue().trim();
            if (!validar(idValue, "Ingrese el ID del parqueadero")) {
                return;
            }
            Parqueadero parqueadero = crudService.buscarPorId(Parqueadero.class, idValue);
            if (parqueadero == null) {
                Notification.show("No se encontró el parqueadero");
                return;
            }
            parqueadero.setTipo(tipo.getValue());
            parqueadero.setIdPropietario(idPropietario.getValue());
            parqueadero.setIdResidente(idResidente.getValue());
            parqueadero.setIdApto(idApartamento.getValue());
            crudService.actualizar(parqueadero);
            refrescar.run();
            Notification.show("Parqueadero actualizado");
        });
        Button eliminar = new Button("Eliminar", event -> {
            String idValue = id.getValue().trim();
            if (!validar(idValue, "Ingrese el ID del parqueadero")) {
                return;
            }
            if (crudService.eliminar(Parqueadero.class, idValue)) {
                refrescar.run();
                limpiar(id, tipo, idPropietario, idResidente, idApartamento);
                grid.deselectAll();
                Notification.show("Parqueadero eliminado");
            } else {
                Notification.show("No se encontró el parqueadero");
            }
        });
        eliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        layout.add(crearDescargas("Parqueaderos", "parqueaderos", Parqueadero.class,
                        List.of("ID", "Tipo", "ID del propietario", "ID del residente", "ID del apartamento"),
                        parqueadero -> List.of(valor(parqueadero.getIdParqueadero()), valor(parqueadero.getTipo()),
                                valor(parqueadero.getIdPropietario()), valor(parqueadero.getIdResidente()),
                                valor(parqueadero.getIdApto()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                        botonLimpiar(grid, id, tipo, idPropietario, idResidente, idApartamento)), grid);
        return layout;
    }

    private Component crearSeccionCuartosUtiles() {
        VerticalLayout layout = new VerticalLayout();
        TextField id = new TextField("ID del cuarto útil");
        id.setReadOnly(true);
        TextField area = new TextField("Área");
        TextField idPropietario = new TextField("ID del propietario");
        TextField idResidente = new TextField("ID del residente");
        TextField idApartamento = new TextField("ID del apartamento");
        FormLayout form = new FormLayout(id, area, idPropietario, idResidente, idApartamento);

        Grid<CuartoUtil> grid = new Grid<>(CuartoUtil.class, false);
        grid.addColumn(CuartoUtil::getIdCuartoUtil).setHeader("ID").setAutoWidth(true);
        grid.addColumn(CuartoUtil::getArea).setHeader("Área").setAutoWidth(true);
        grid.addColumn(CuartoUtil::getIdPopietario).setHeader("ID del propietario").setAutoWidth(true);
        grid.addColumn(CuartoUtil::getIdResidente).setHeader("ID del residente").setAutoWidth(true);
        grid.addColumn(CuartoUtil::getIdApto).setHeader("ID del apartamento").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(CuartoUtil.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(cuarto -> {
            id.setValue(valor(cuarto.getIdCuartoUtil()));
            area.setValue(valor(cuarto.getArea()));
            idPropietario.setValue(valor(cuarto.getIdPopietario()));
            idResidente.setValue(valor(cuarto.getIdResidente()));
            idApartamento.setValue(valor(cuarto.getIdApto()));
        }));

        Button crear = botonCrear(() -> {
            CuartoUtil cuarto = new CuartoUtil(null, area.getValue(), idPropietario.getValue(),
                    idResidente.getValue(), idApartamento.getValue());
            crudService.guardar(cuarto);
            refrescar.run();
            id.setValue(valor(cuarto.getIdCuartoUtil()));
            Notification.show("Cuarto útil guardado con ID: " + cuarto.getIdCuartoUtil());
        });
        Button consultar = botonConsultar(grid, CuartoUtil.class, id, "Cuarto útil");
        Button actualizar = new Button("Actualizar", event -> {
            Long idValue = parseId(id);
            if (idValue == null) {
                return;
            }
            CuartoUtil cuarto = crudService.buscarPorId(CuartoUtil.class, idValue);
            if (cuarto == null) {
                Notification.show("No se encontró el cuarto útil");
                return;
            }
            cuarto.setArea(area.getValue());
            cuarto.setIdPopietario(idPropietario.getValue());
            cuarto.setIdResidente(idResidente.getValue());
            cuarto.setIdApto(idApartamento.getValue());
            crudService.actualizar(cuarto);
            refrescar.run();
            Notification.show("Cuarto útil actualizado");
        });
        Button eliminar = botonEliminar(grid, CuartoUtil.class, id, "Cuarto útil",
                () -> limpiar(id, area, idPropietario, idResidente, idApartamento), refrescar);
        layout.add(crearDescargas("Cuartos útiles", "cuartos-utiles", CuartoUtil.class,
                        List.of("ID", "Área", "ID del propietario", "ID del residente", "ID del apartamento"),
                        cuarto -> List.of(valor(cuarto.getIdCuartoUtil()), valor(cuarto.getArea()),
                                valor(cuarto.getIdPopietario()), valor(cuarto.getIdResidente()),
                                valor(cuarto.getIdApto()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                        botonLimpiar(grid, id, area, idPropietario, idResidente, idApartamento)), grid);
        return layout;
    }

    private Component crearSeccionResidentes() {
        VerticalLayout layout = new VerticalLayout();
        TextField id = new TextField("ID del residente");
        id.setReadOnly(true);
        TextField nombre = new TextField("Nombre");
        TextField apellidos = new TextField("Apellidos");
        DatePicker fechaNacimiento = new DatePicker("Fecha de nacimiento");
        TextField celular = new TextField("Celular");
        TextField correo = new TextField("Correo");
        FormLayout form = new FormLayout(id, nombre, apellidos, fechaNacimiento, celular, correo);

        Grid<Residente> grid = new Grid<>(Residente.class, false);
        grid.addColumn(Residente::getIdResidente).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Residente::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Residente::getApellidos).setHeader("Apellidos").setAutoWidth(true);
        grid.addColumn(Residente::getFechaDeNacimiento).setHeader("Fecha de nacimiento").setAutoWidth(true);
        grid.addColumn(Residente::getCelular).setHeader("Celular").setAutoWidth(true);
        grid.addColumn(Residente::getCorreo).setHeader("Correo").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Residente.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(residente -> {
            id.setValue(valor(residente.getIdResidente()));
            nombre.setValue(valor(residente.getNombre()));
            apellidos.setValue(valor(residente.getApellidos()));
            fechaNacimiento.setValue(residente.getFechaDeNacimiento());
            celular.setValue(valor(residente.getCelular()));
            correo.setValue(valor(residente.getCorreo()));
        }));

        Button crear = botonCrear(() -> {
            if (!validar(fechaNacimiento.getValue(), "Seleccione la fecha de nacimiento")) {
                return;
            }
            Residente residente = new Residente(null, nombre.getValue(), apellidos.getValue(),
                    fechaNacimiento.getValue(), celular.getValue(), correo.getValue());
            crudService.guardar(residente);
            refrescar.run();
            id.setValue(valor(residente.getIdResidente()));
            Notification.show("Residente guardado con ID: " + residente.getIdResidente());
        });
        Button consultar = botonConsultar(grid, Residente.class, id, "Residente");
        Button actualizar = new Button("Actualizar", event -> {
            Long idValue = parseId(id);
            if (idValue == null || !validar(fechaNacimiento.getValue(), "Seleccione la fecha de nacimiento")) {
                return;
            }
            Residente residente = crudService.buscarPorId(Residente.class, idValue);
            if (residente == null) {
                Notification.show("No se encontró el residente");
                return;
            }
            residente.setNombre(nombre.getValue());
            residente.setApellidos(apellidos.getValue());
            residente.setFechaDeNacimiento(fechaNacimiento.getValue());
            residente.setCelular(celular.getValue());
            residente.setCorreo(correo.getValue());
            crudService.actualizar(residente);
            refrescar.run();
            Notification.show("Residente actualizado");
        });
        Button eliminar = botonEliminar(grid, Residente.class, id, "Residente",
                () -> {
                    limpiar(id, nombre, apellidos, celular, correo);
                    fechaNacimiento.clear();
                }, refrescar);
        layout.add(crearDescargas("Residentes", "residentes", Residente.class,
                        List.of("ID", "Nombre", "Apellidos", "Fecha de nacimiento", "Celular", "Correo"),
                        residente -> List.of(valor(residente.getIdResidente()), valor(residente.getNombre()),
                                valor(residente.getApellidos()), valor(residente.getFechaDeNacimiento()),
                                valor(residente.getCelular()), valor(residente.getCorreo()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                botonLimpiar(grid, id, nombre, apellidos, celular, correo, fechaNacimiento)), grid);
        return layout;
    }

    private Component crearSeccionUsuarios() {
        VerticalLayout layout = new VerticalLayout();
        TextField id = new TextField("ID");
        id.setReadOnly(true);
        TextField nombre = new TextField("Nombre");
        TextField apellido = new TextField("Apellido");
        TextField email = new TextField("Email");
        TextField telefono = new TextField("Teléfono");
        TextField direccion = new TextField("Dirección");
        TextField rol = new TextField("Rol");
        TextField estado = new TextField("Estado");
        FormLayout form = new FormLayout(id, nombre, apellido, email, telefono, direccion, rol, estado);

        Grid<Usuario> grid = new Grid<>(Usuario.class, false);
        grid.addColumn(Usuario::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellido).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Usuario::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Usuario::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(Usuario::getRol).setHeader("Rol").setAutoWidth(true);
        grid.addColumn(Usuario::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(Usuario::getFechaRegistro).setHeader("Fecha de registro").setAutoWidth(true);
        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Usuario.class));
        refrescar.run();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(usuario -> {
            id.setValue(valor(usuario.getId()));
            nombre.setValue(valor(usuario.getNombre()));
            apellido.setValue(valor(usuario.getApellido()));
            email.setValue(valor(usuario.getEmail()));
            telefono.setValue(valor(usuario.getTelefono()));
            direccion.setValue(valor(usuario.getDireccion()));
            rol.setValue(valor(usuario.getRol()));
            estado.setValue(valor(usuario.getEstado()));
        }));

        Button crear = botonCrear(() -> {
            Usuario usuario = new Usuario(nombre.getValue(), apellido.getValue(), email.getValue(),
                    telefono.getValue(), direccion.getValue(), rol.getValue(), estado.getValue(), LocalDateTime.now());
            crudService.guardar(usuario);
            refrescar.run();
            id.setValue(valor(usuario.getId()));
            Notification.show("Usuario guardado con ID: " + usuario.getId());
        });
        Button consultar = botonConsultar(grid, Usuario.class, id, "Usuario");
        Button actualizar = new Button("Actualizar", event -> {
            Long idValue = parseId(id);
            if (idValue == null) {
                return;
            }
            Usuario usuario = crudService.buscarPorId(Usuario.class, idValue);
            if (usuario == null) {
                Notification.show("No se encontró el usuario");
                return;
            }
            usuario.setNombre(nombre.getValue());
            usuario.setApellido(apellido.getValue());
            usuario.setEmail(email.getValue());
            usuario.setTelefono(telefono.getValue());
            usuario.setDireccion(direccion.getValue());
            usuario.setRol(rol.getValue());
            usuario.setEstado(estado.getValue());
            crudService.actualizar(usuario);
            refrescar.run();
            Notification.show("Usuario actualizado");
        });
        Button eliminar = botonEliminar(grid, Usuario.class, id, "Usuario",
                () -> limpiar(id, nombre, apellido, email, telefono, direccion, rol, estado), refrescar);
        layout.add(crearDescargas("Usuarios", "usuarios", Usuario.class,
                        List.of("ID", "Nombre", "Apellido", "Email", "Teléfono", "Dirección", "Rol", "Estado",
                                "Fecha de registro"),
                        usuario -> List.of(valor(usuario.getId()), valor(usuario.getNombre()),
                                valor(usuario.getApellido()), valor(usuario.getEmail()), valor(usuario.getTelefono()),
                                valor(usuario.getDireccion()), valor(usuario.getRol()), valor(usuario.getEstado()),
                                valor(usuario.getFechaRegistro()))),
                form, crearAcciones(crear, consultar, actualizar, eliminar,
                botonLimpiar(grid, id, nombre, apellido, email, telefono, direccion, rol, estado)), grid);
        return layout;
    }

    private <T> Button botonConsultar(Grid<T> grid, Class<T> tipo, TextField id, String entidad) {
        return new Button("Consultar", event -> {
            Long idValue = parseId(id);
            if (idValue == null) {
                return;
            }
            T registro = crudService.buscarPorId(tipo, idValue);
            if (registro == null) {
                Notification.show("No se encontró " + entidad.toLowerCase());
            } else {
                grid.select(registro);
            }
        });
    }
    /* FUNCIÓN PARA EXPORTAR EN FORMATO PDF Y EXCEL */
    private <T> HorizontalLayout crearDescargas(String titulo, String nombreArchivo, Class<T> tipo,
            List<String> encabezados, Function<T, List<String>> convertirFila) {
        StreamResource recursoPdf = new StreamResource(nombreArchivo + ".pdf", () -> {
            List<List<String>> filas = crudService.listarTodos(tipo).stream().map(convertirFila).toList();
            return new ByteArrayInputStream(exportService.generarPdf(titulo, encabezados, filas));
        });
        recursoPdf.setContentType("application/pdf");

        StreamResource recursoExcel = new StreamResource(nombreArchivo + ".xlsx", () -> {
            List<List<String>> filas = crudService.listarTodos(tipo).stream().map(convertirFila).toList();
            return new ByteArrayInputStream(exportService.generarExcel(titulo, encabezados, filas));
        });
        recursoExcel.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        Anchor descargarPdf = new Anchor(recursoPdf, "Descargar PDF");
        descargarPdf.getElement().setAttribute("download", true);
        descargarPdf.getElement().setAttribute("theme", "button");

        Anchor descargarExcel = new Anchor(recursoExcel, "Descargar Excel");
        descargarExcel.getElement().setAttribute("download", true);
        descargarExcel.getElement().setAttribute("theme", "button");
        
        
        
        
        /* BOTÓN PARA EXPORTAR EN FORMATO PDF Y EXCEL */
        HorizontalLayout descargas = new HorizontalLayout(descargarPdf, descargarExcel);
        descargas.getStyle().set("flex-wrap", "wrap");
        return descargas;
    }

    private <T> Button botonEliminar(Grid<T> grid, Class<T> tipo, TextField id, String entidad,
            Runnable limpiarCampos, Runnable refrescar) {
        Button boton = new Button("Eliminar", event -> {
            Long idValue = parseId(id);
            if (idValue == null) {
                return;
            }
            if (crudService.eliminar(tipo, idValue)) {
                refrescar.run();
                limpiarCampos.run();
                grid.deselectAll();
                Notification.show(entidad + " eliminado");
            } else {
                Notification.show("No se encontró " + entidad.toLowerCase());
            }
        });
        boton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return boton;
    }

    private Button botonCrear(Runnable accion) {
        Button boton = new Button("Crear", event -> accion.run());
        boton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return boton;
    }

    private Button botonLimpiar(Grid<?> grid, Component... campos) {
        return new Button("Limpiar", event -> {
            limpiar(campos);
            grid.deselectAll();
        });
    }

    private Long parseId(TextField field) {
        String value = field.getValue().trim();
        if (value.isEmpty()) {
            Notification.show("Ingrese un identificador");
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            Notification.show("El identificador debe ser numérico");
            return null;
        }
    }

    private boolean validar(String value, String mensaje) {
        if (value == null || value.isBlank()) {
            Notification.show(mensaje);
            return false;
        }
        return true;
    }

    private boolean validar(LocalDate value, String mensaje) {
        if (value == null) {
            Notification.show(mensaje);
            return false;
        }
        return true;
    }

    private String valor(Object value) {
        return value == null ? "" : value.toString();
    }

    private void limpiar(Component... campos) {
        for (Component campo : campos) {
            if (campo instanceof TextField textField) {
                textField.clear();
            } else if (campo instanceof DatePicker datePicker) {
                datePicker.clear();
            }
        }
    }

    private HorizontalLayout crearAcciones(Button... botones) {
        HorizontalLayout layout = new HorizontalLayout(botones);
        layout.getStyle().set("flex-wrap", "wrap");
        return layout;
    }
}
