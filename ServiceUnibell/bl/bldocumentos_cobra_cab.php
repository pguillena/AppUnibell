<?php
class bldocumentos_cobra_cab {
    protected $app;
    protected $response;
    protected $request;
    protected $cnn;
       
    public function __construct(){
        $this->app = \Slim\Slim::getInstance();
        $this->response = \Slim\Slim::getInstance()->response();
        $this->request = \Slim\Slim::getInstance()->request();      
    }
   
    public function SelectAll($p1,$p2,$p3,$p4,$p5){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LISTAR_DOCUMENTOS_COBRA_CAB(:p1,:p2,:p3,:p4,:p5,:data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght            
            oci_bind_by_name($stmt, ':p1', $p1); 
            oci_bind_by_name($stmt, ':p2', $p2); 
            oci_bind_by_name($stmt, ':p3', $p3); 
            oci_bind_by_name($stmt, ':p4', $p4); 
            oci_bind_by_name($stmt, ':p5', $p5);
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

 public function CobranzaFlujo1($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8,$p9,$p10,$p11,$p12,$p13,$p14,$p15,$p16,$p17){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LIQUI_COBRANZA_CRED_COB(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12,:p13,:p14,:p15,:p16,:p17,:data); end;");
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


      public function InsertCobranza(){
        $this->response->header("Content-type", "application/json");
        //$this->response->header("Content-Encoding", "gzip");        
        $root = json_decode($this->app->request->getBody());
        $body = json_decode($this->app->request->getBody(),true);        
        try{            
            $cnn = getConnectionOracle();
            //$cnn = getConnectionOracle($root->dbuser, desencriptar($root->dbpass));
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.INSERTAR_COBRANZA_MOBIL(:p1,:data); end;");
            $curs = oci_new_cursor($cnn);
                     
            // creating object of SimpleXMLElement
            $xml = new SimpleXMLElement("<?xml version='1.0' encoding='utf-8' ?><COBRANZA></COBRANZA>");
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

     public function InsertUpdateCobranza(){
        $this->response->header("Content-type", "application/json");
        //$this->response->header("Content-Encoding", "gzip");        
        $root = json_decode($this->app->request->getBody());
        $body = json_decode($this->app->request->getBody(),true);        
        try{            
            $cnn = getConnectionOracle();
            //$cnn = getConnectionOracle($root->dbuser, desencriptar($root->dbpass));
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.INSERT_UPDATE_COBRANZA_MOBIL(:p1,:data); end;");
            $curs = oci_new_cursor($cnn);
                     
            // creating object of SimpleXMLElement
            $xml = new SimpleXMLElement("<?xml version='1.0' encoding='utf-8' ?><COBRANZA></COBRANZA>");
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

    public function UpdateCobranza(){
        $this->response->header("Content-type", "application/json");
        //$this->response->header("Content-Encoding", "gzip");        
        $root = json_decode($this->app->request->getBody());
        $body = json_decode($this->app->request->getBody(),true);        
        try{            
            $cnn = getConnectionOracle();
            //$cnn = getConnectionOracle($root->dbuser, desencriptar($root->dbpass));
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.UPDATE_COBRANZA_MOBIL(:p1,:data); end;");
            $curs = oci_new_cursor($cnn);
                     
            // creating object of SimpleXMLElement
            $xml = new SimpleXMLElement("<?xml version='1.0' encoding='utf-8' ?><COBRANZA></COBRANZA>");
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

    public function DeleteCobranza(){
        $this->response->header("Content-type", "application/json");
        //$this->response->header("Content-Encoding", "gzip");        
        $root = json_decode($this->app->request->getBody());
        $body = json_decode($this->app->request->getBody(),true);        
        try{            
            $cnn = getConnectionOracle();
            //$cnn = getConnectionOracle($root->dbuser, desencriptar($root->dbpass));
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.DELETE_COBRANZA_MOBIL(:p1,:data); end;");
            $curs = oci_new_cursor($cnn);
                     
            // creating object of SimpleXMLElement
            $xml = new SimpleXMLElement("<?xml version='1.0' encoding='utf-8' ?><COBRANZA></COBRANZA>");
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




 public function ConciliarDepositos($p1,$p2,$p3,$p4,$p5,$p6,$p7){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.CONCILIA_DEPOSITOS(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:data); end;");
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

 public function GenerarPlanillaCobranza($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8,$p9,$p10){
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.GENERAR_PLANILLA_COBRANZA(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:data); end;");
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

public function InsertarPlanilla($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8,$p9,$p10,$p11,$p12,$p13,$p14){          
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
           $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.INSERTAR_PLANILLA_COBRANZA(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12,:p13,:p14,:data); end;");
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
            oci_bind_by_name($stmt, ':p10',$p10);
            oci_bind_by_name($stmt, ':p11',$p11); 
            oci_bind_by_name($stmt, ':p12',$p12); 
            oci_bind_by_name($stmt, ':p13',$p13); 
            oci_bind_by_name($stmt, ':p14',$p14); 
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

    public function RetornarPlanilla($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8,$p9){
        $this->response->header("Content-type", "application/json"); 
        try{
            $cnn = getConnectionOracle();
         $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.RETORNAR_PLANILLA(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:data); end;");
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



    public function LiquidacionCobranza($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8){
            $this->response->header("Content-type", "application/json");
            try{
                $cnn = getConnectionOracle();
                $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LIQUIDACION_COBRANZA(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:data); end;");
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

 public function LiquidacionCobranzaGrupo($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8){
            $this->response->header("Content-type", "application/json");
            try{
                $cnn = getConnectionOracle();
                $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.LIQUIDACION_COBRANZA_GRUPO(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:data); end;");
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



 public function RptCobranza($p1,$p2,$p3,$p4,$p5,$p6,$p7,$p8,$p9,$p10,$p11,$p12,$p13,$p14,$p15,$p16){
            $this->response->header("Content-type", "application/json");
            try{
                $cnn = getConnectionOracle();
                $stmt = oci_parse($cnn,"begin PKG_MS_COBRANZA.RPT_COBRANZAS(:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12,:p13,:p14,:p15,:p16,:data); end;");
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
            oci_bind_by_name($stmt, ':p10',$p10);
            oci_bind_by_name($stmt, ':p11',$p11);
            oci_bind_by_name($stmt, ':p12',$p12);
            oci_bind_by_name($stmt, ':p13',$p13);
            oci_bind_by_name($stmt, ':p14',$p14);
            oci_bind_by_name($stmt, ':p15',$p15);
            oci_bind_by_name($stmt, ':p16',$p16);
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
