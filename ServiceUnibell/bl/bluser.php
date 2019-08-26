<?php
class bluser {
    protected $app;
    protected $response;
    protected $request;
    protected $cnn;
    protected $cia;
    protected $suc;
    
    public function __construct(){
        $this->app = \Slim\Slim::getInstance();
        $this->response = \Slim\Slim::getInstance()->response();
        $this->request = \Slim\Slim::getInstance()->request();
        $this->cia ='001';
        $this->suc ='001';
    }
   
    public function SelectRolByCodUsu($codusu){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();            
            $stmt = oci_parse($cnn,"begin COM02.p_validar_rol(:p1, :p2, :data); end;");
            //Send parameters variable  value  lenght
            $p2='ROLSCP_SISTEMAS';
            $p3=0;
            oci_bind_by_name($stmt, ':p1', $codusu);
            oci_bind_by_name($stmt, ':p2', $p2);
            oci_bind_by_name($stmt, ':data', $p3, 40);
            //Execute Statement
            oci_execute($stmt);
            
            echo '{"status": true, "message":" 1 Registro(s) Encontrado(s)", "resultado":' . $p3 . '}';            
        } catch (Exception $e) {
           echo '{"status": false, "message":"'. fnCleaner($e->getMessage()) .'"}';
        }
    }
    
    public function SelectUserByCodigo($codigo){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();            
            $stmt = oci_parse($cnn,"begin CRMSOL.PKG_CRM.p_SELECTUsuarioByCodigo(:p1, :p2, :p3,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght
            oci_bind_by_name($stmt, ':p1', $this->cia , 3);
            oci_bind_by_name($stmt, ':p2', $this->suc , 3);
            oci_bind_by_name($stmt, ':p3', $codigo , 3);
            //Bind Cursor     put -1 
            oci_bind_by_name($stmt, ':data', $curs, -1, OCI_B_CURSOR);
            //Execute Statement
            oci_execute($stmt);
            oci_execute($curs);            
            $pila=array();
            $cont=0;
             if($obj = oci_fetch_object($curs)){
                 do{
                     array_push($pila, $obj);
                     $cont++; 
                 } while($obj = \oci_fetch_object($curs));
             }
            echo '{"status": true, "message":"' . $cont . ' Registro(s) Encontrado(s)", "usuarios":' . json_encode($pila) . '}';            
        } catch (Exception $e) {
            echo '{"status": false, "message":"'. fnCleaner($e->getMessage()) .'"}';
        }
    }
    
    public function LogUsuarioOracle() {
        $this->response->header("Content-type", "application/json");
        $body = json_decode($this->app->request->getBody());
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin CRMSOL.PKG_CRM.p_SELECTUsuarioByLogin(:p1, :p2, :p3, :p4, :data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght
            oci_bind_by_name($stmt, ':p1', $this->cia , 3);
            oci_bind_by_name($stmt, ':p2', $this->suc , 3);
            oci_bind_by_name($stmt, ':p3', $body->USU_DESCRI);
            oci_bind_by_name($stmt, ':p4', $body->USU_PASSWD); 
            //Bind Cursor     put -1 
            oci_bind_by_name($stmt, ':data', $curs, -1, OCI_B_CURSOR);
            //Execute Statement
            oci_execute($stmt);
            oci_execute($curs);            
            $pila=array();
            $cont=0;
            if($obj = oci_fetch_object($curs)){
                 //do{
                 //    array_push($pila, $obj);
                     $cont++; 
                 //} while($obj = \oci_fetch_object($curs));
            }
            if ($cont >= 1){        
                echo '{"status": true, "message":"Bienvenido a asu app Movil \n'. $body->USU_DESCRI .'", "usuario":'. json_encode($obj) .'}';
            }else{
                echo '{"status": false, "message":" Usuario o Contraseña Incorrecta"}';
            }
        } catch (Exception $e) {
            echo '{"status": false, "message":"'. fnCleaner($e->getMessage()) .'"}';
        }
    }
    
    public function LogUsuarioOracleByImei() {
        $this->response->header("Content-type", "application/json");
        $body = json_decode($this->app->request->getBody());
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin CRMSOL.PKG_CRM.p_SELECTUsuarioByImei(:p1, :p2, :p3, :p4, :p5, :p6, :p7, :data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght
            oci_bind_by_name($stmt, ':p1', $this->cia , 3);
            oci_bind_by_name($stmt, ':p2', $this->suc , 3);
            oci_bind_by_name($stmt, ':p3', $body->USU_DESCRI);
            oci_bind_by_name($stmt, ':p4', $body->USU_PASSWD);
            oci_bind_by_name($stmt, ':p5', $body->LOG_AMBITO);
            oci_bind_by_name($stmt, ':p6', $body->PHO_NROIMEI);
            oci_bind_by_name($stmt, ':p7', $body->PHO_NROVER);
            //Bind Cursor     put -1 
            oci_bind_by_name($stmt, ':data', $curs, -1, OCI_B_CURSOR);
            //Execute Statement
            oci_execute($stmt);
            oci_execute($curs);            
            $pila=array();
            $cont=0;
            $imei="0";
            if($obj = oci_fetch_object($curs)){
                array_push($pila, $obj);
                $cont++; 
            }
            if ($cont >= 1){
                // Use nombres de atributo en mayúsculas para cada columna estándar de Oracle
                if($obj->TMPCTRLIMEI === "0"){
                     echo '{"status": false, "message": "(IMEI) no tiene permisos,\n\ncomuniquese con sistemas", "usuario":[]}';
                }else{                    
                    echo '{"status": true, "message": "Bienvenido a CRM Movil \n'. $body->USU_DESCRI .'", "usuario":'. json_encode($obj) .'}';
                }
            }else{
                echo '{"status": false, "message":" Usuario o Contraseña Incorrecta"}';
            }
        } catch (Exception $e) {
            echo '{"status": false, "message":"'. fnCleaner($e->getMessage()) .'"}';
        } 
    }

      public function SelectRol($codusu,$codrol){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin CRMSOL.PKG_CRM.P_SELECTRol(:p1, :p2,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght
            oci_bind_by_name($stmt, ':p1', $codusu);
            oci_bind_by_name($stmt, ':p2', $codrol);           
            //Bind Cursor put -1 
            oci_bind_by_name($stmt, ':data', $curs, -1, OCI_B_CURSOR);
            //Execute Statement
            oci_execute($stmt);
            oci_execute($curs);            
            $pila=array();
            $cont=0;
             if($obj = oci_fetch_object($curs)){
                 do{
                     array_push($pila, $obj);
                     $cont++; 
                 } while($obj = \oci_fetch_object($curs));
             }
            echo '{"status": true, "message":"' . $cont . ' Registro(s) Encontrado(s)", "result":' . json_encode($pila) . '}';            
        } catch (Exception $e) {
            echo '{"status": false, "message":"'. fnCleaner($e->getMessage()) .'"}';
        }
    }



    
}
