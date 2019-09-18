<?php
    function fnCleaner($Cadena){
     $order   = array("\r\n", "\n", "\r");
     $replace = '';
     return str_replace($order, $replace, $Cadena);
    }
    function ARRAYtoXML($array, $depth = 0){
        $indent = '';
        $return = '';
        for($i = 0; $i < $depth; $i++)
            $indent .= "\t";
        foreach($array as $key => $item){
            $return .= "{$indent}< {$key}>\n";
            if(is_array($item))
                $return .= ARRAYtoXML($item, $depth + 1);
            else
                $return .= "{$indent}\t< ![CDATA[{$item}]]>\n";
                $return .= "{$indent}\n";
            }
        return $return;
    }
    
    function array_to_xml( $data, &$xml_data ) {
        foreach( $data as $key => $value ) {
            if( is_array($value) ) {
                if( is_numeric($key) ){
                    $key = 'item'.$key; //dealing with <0/>..<n/> issues
                }
                $subnode = $xml_data->addChild($key);
                array_to_xml($value, $subnode);
            } else {
                $xml_data->addChild("$key",htmlspecialchars("$value"));
            }
         }
    }
    
    function array2xml($array, $xml = false){
        if($xml === false){
            $xml = new SimpleXMLElement('<root/>');
        }
        foreach($array as $key => $value){
            if(is_array($value)){
                //$xml->addChild('root');
                //array2xml($value, $xml->addChild($key)); 
                array2xml($value, $xml->addChild('root')); 
            }else{
                $xml->addChild($key, $value);
            }
        }
        return $xml->asXML();
    }
    
    //PRE PRODUCCION======>$dbuser = "CONEXO7" ,$dbpass = "FCC0O7"
    //DESARROLLO======>$dbuser = "CONEXO7" ,$dbpass = "FCC0O7"
    function getConnectionOracle($dbuser = "CONEXO7" ,$dbpass = "FCC0O7"){
		$username = "UNIBELL";
		$password = "I3B4E5";
		$dbhost="172.16.1.66";
		$dbserv="UNIBD";

		$db = "(DESCRIPTION=(ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 172.16.1.66)(PORT = 1521)))(CONNECT_DATA=(SID = UNIBD)))";
		$conn= oci_connect($username, $password, $dbhost . '/'. $dbserv, 'AL32UTF8');
        
        if (!$conn) {
            $e = oci_error();
            trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
        }
        return $conn;
    }

    function LogUsuarioOracle() {
        $response = \Slim\Slim::getInstance()->response();
        $response->header("Content-type", "application/json");
        $request = \Slim\Slim::getInstance()->request();
        $body = json_decode($request->getBody());
        $cia ='001';
        $suc ='001';
        try{
            $cnn = getConnectionOracle();
            $stmt = oci_parse($cnn,"begin CRMSOL.PKG_CRM.p_SELECTUsuarioByLogin(:p1, :p2, :p3, :p4, :data); end;");
            $curs = oci_new_cursor($cnn);
            //Send parameters variable  value  lenght
            oci_bind_by_name($stmt, ':p1', $cia , 3);
            oci_bind_by_name($stmt, ':p2', $suc , 3);
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
                echo '{"status": true, "message":"Bienvenido a CRM Movil \n'. $body->USU_DESCRI .'", "usuario":'. json_encode($obj) .'}';
            }else{
                echo '{"status": false, "message":" Usuario o Contraseña Incorrecta"}';
            }
        } catch (Exception $e) {
            echo '{"status": false, "message":"'. fnCleaner($e->getMessage()) .'"}';
        }
    }
?>