package Controller;

import Model.CajeroModel;
import View.CajeroView;

public class CajeroController {
    private CajeroModel model;
    private CajeroView view;
    private boolean sistemaActivo;

    public CajeroController(){
        this.model = model;
        this.view = view;
        this.sistemaActivo = true;
    }
    public void iniciarSistema(){
        view.mostrarBienvenida();
        while (sistemaActivo){
            if(autenticarUsuario()){
                ejecutarMenuPrincipal();
            }else{
                view.mostrarMensaje("Credenciales incorrectas");
            }
        }
        view.mostrarMensaje("Gracias por usar nuestro cajero");
    }
    private boolean autenticarUsuario(){
        String numeroCuenta = view.solicitarNumeroCuenta();
        String pin = view.solicitarPin();
        return model.autenticacion(numeroCuenta, pin);
    }
    private void ejecutarMenuPrincipal(){

    }
}
