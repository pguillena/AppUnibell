<?php
class bls_inv_inventario {
    protected $app;
    protected $response;
    protected $request;
    protected $cnn;
       
    public function __construct(){
        $this->app = \Slim\Slim::getInstance();
        $this->response = \Slim\Slim::getInstance()->response();
        $this->request = \Slim\Slim::getInstance()->request();      
    }   

      public function InsertInventario(){
        $this->response->header("Content-type", "application/json");
        //$this->response->header("Content-Encoding", "gzip");        
        $root = json_decode($this->app->request->getBody());
        $body = json_decode($this->app->request->getBody(),true);        
        try{            
            $cnn = getConnectionOracle();
            //$cnn = getConnectionOracle($root->dbuser, desencriptar($root->dbpass));
            $stmt = oci_parse($cnn,"begin PKG_MS_INVENTARIO.INSERTAR_INVENTARIO(:p1,:data); end;");
            $curs = oci_new_cursor($cnn);
                     
            // creating object of SimpleXMLElement
            $xml = new SimpleXMLElement("<?xml version='1.0' encoding='utf-8' ?><INVENTARIO></INVENTARIO>");
            $dato = array2xml($body,$xml);
            
            //Send parameters variable  value  lenght            
            $lob = oci_new_descriptor($cnn, OCI_D_LOB);
            oci_bind_by_name($stmt, ':p1', $lob, -1,OCI_B_CLOB);
            $lob->writeTemporary($dato);             
            
            //oci_bind_by_name($stmt, ':p1', $dato);
            oci_bind_by_name($stmt, ':data',$curs,-1,OCI_B_CURSOR);
            oci_execute($stmt, OCI_DEFAULT);
            oci_execute($curs);
            $pila=array();
            $cont=0;
             if($obj = oci_fetch_object($curs)){
                 do{
                     array_push($pila, $obj);
                     $cont++; 
                 } while($obj = \oci_fetch_object($curs));
             }
            //echo '{"status": true, "message":' .  $cont . ' Registro Insertado", "result":' . json_encode($pila) . '}';
            echo '{"status": 1, "message":"' .  $cont . ' Registro(s) Encontrado(s)", "datos":'  . json_encode($pila) . '}';     
        }catch(Exception $e){
            echo '{"status": 0, "message": " WBS'. fnCleaner($e->getMessage()) .' "}';        
        }   
    } 






}
