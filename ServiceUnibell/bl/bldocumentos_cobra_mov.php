<?php
class bldocumentos_cobra_mov {
    protected $app;
    protected $response;
    protected $request;
    protected $cnn;
       
    public function __construct(){
        $this->app = \Slim\Slim::getInstance();
        $this->response = \Slim\Slim::getInstance()->response();
        $this->request = \Slim\Slim::getInstance()->request();      
    }
   
    public function SelectAll($p1,$p2,$p3){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LISTAR_DOCUMENTOS_COBRA_MOV(:p1,:p2,:p3,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght            
            oci_bind_by_name($stmt, ':p1', $p1); 
            oci_bind_by_name($stmt, ':p2', $p2); 
            oci_bind_by_name($stmt, ':p3', $p3); 
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

 public function CobranzaFlujoResumen($p1,$p2){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LISTAR_MOV_X_PLANILLA(:p1,:p2,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght            
            oci_bind_by_name($stmt, ':p1', $p1); 
            oci_bind_by_name($stmt, ':p2', $p2);            
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

     public function CobranzaFlujoPlanilla2($p1,$p2,$p3){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.FLUJO_PLANILLA_COBRANZA(:p1,:p2,:p3,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght            
            oci_bind_by_name($stmt, ':p1', $p1); 
            oci_bind_by_name($stmt, ':p2', $p2); 
            oci_bind_by_name($stmt, ':p3', $p3); 
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

     public function CobranzaFlujoPlanilla3($p1,$p2,$p3,$p4,$p5,$p6,$p7){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LISTAR_AUDITORIA(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght            
            oci_bind_by_name($stmt, ':p1', $p1); 
            oci_bind_by_name($stmt, ':p2', $p2); 
            oci_bind_by_name($stmt, ':p3', $p3); 
            oci_bind_by_name($stmt, ':p4', $p4); 
            oci_bind_by_name($stmt, ':p5', $p5); 
            oci_bind_by_name($stmt, ':p6', $p6); 
            oci_bind_by_name($stmt, ':p7', $p7); 
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

     public function SelectAPlailla($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8,$p9,$p10,$p11,$p12,$p13,$p14,$p15,$p16,$p17,$p18,$p19){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LISTAR_DOC_COBRA_PAGINADO(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12,:p13,:p14,:p15,:p16,:p17,:p18,:p19,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght            
            oci_bind_by_name($stmt, ':p1', $p1); 
            oci_bind_by_name($stmt, ':p2', $p2); 
            oci_bind_by_name($stmt, ':p3', $p3);
            oci_bind_by_name($stmt, ':p4', $p4); 
            oci_bind_by_name($stmt, ':p5', $p5); 
            oci_bind_by_name($stmt, ':p6', $p6); 
            oci_bind_by_name($stmt, ':p7', $p7); 
            oci_bind_by_name($stmt, ':p8', $p8); 
            oci_bind_by_name($stmt, ':p9', $p9); 
            oci_bind_by_name($stmt, ':p10', $p10); 
            oci_bind_by_name($stmt, ':p11', $p11); 
            oci_bind_by_name($stmt, ':p12', $p12); 
            oci_bind_by_name($stmt, ':p13', $p13); 
            oci_bind_by_name($stmt, ':p14', $p14); 
            oci_bind_by_name($stmt, ':p15', $p15); 
            oci_bind_by_name($stmt, ':p16', $p16); 
            oci_bind_by_name($stmt, ':p17', $p17); 
            oci_bind_by_name($stmt, ':p18', $p18); 
            oci_bind_by_name($stmt, ':p19', $p19);            
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
 
     public function InsertDocumentosMov(){
        $this->response->header("Content-type", "application/json");
        //$this->response->header("Content-Encoding", "gzip");        
        $root = json_decode($this->app->request->getBody());
        $body = json_decode($this->app->request->getBody(),true);        
        try{            
            $cnn = getConnectionOracle();
            //$cnn = getConnectionOracle($root->dbuser, desencriptar($root->dbpass));

            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.INSERT_DOCUMENTOS_COBRA_MOV(:p1,:data); end;");
            $curs = oci_new_cursor($cnn);
                     
            // creating object of SimpleXMLElement
            $xml = new SimpleXMLElement("<?xml version='1.0' encoding='utf-8' ?><MOVIMIENTOS></MOVIMIENTOS>");
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
            echo '{"status": true, "message":"' .  $cont . ' Registro Insertado", "result":' . json_encode($pila) . '}';     
        }catch(Exception $e){
            echo '{"status": false, "message": " WBS'. fnCleaner($e->getMessage()) .' "}';        
        }   
    } 


    

}
