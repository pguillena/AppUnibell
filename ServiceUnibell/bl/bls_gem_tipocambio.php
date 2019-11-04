<?php
class bls_gem_tipocambio {
    protected $app;
    protected $response;
    protected $request;
    protected $cnn;
       
    public function __construct(){
        $this->app = \Slim\Slim::getInstance();
        $this->response = \Slim\Slim::getInstance()->response();
        $this->request = \Slim\Slim::getInstance()->request();      
    }
   
    public function SelectAll(){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LISTAR_S_GEM_TIPOCAMBIO(:data); end;");
            $curs = oci_new_cursor($cnn); 
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
            echo '{"status": 1, "message":"' . $cont . ' Registro(s) Encontrado(s)", "datos":' . json_encode($pila) . '}';            
        } catch (Exception $e) {
            echo '{"status": 0, "message":"'. fnCleaner($e->getMessage()) .'"}';
        }
    }
    

}
