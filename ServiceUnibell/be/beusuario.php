<?php
/**
 * Description of beusuario
 *
 * @author TORRES
 */
class beusuario {
    private $usu_codigo;
    private $usu_nombre;
    private $login;
    private $password;
    private $estado;
    private $error;
    private $resultado;
    private $idResultado;
    
    public function __get($property) {
        if (property_exists($this, $property)) {
            return $this->$property;
        }
    }

    public function __set($property, $value) {
        if (property_exists($this, $property)) {
            $this->$property = $value;
        }
    }
    
    function __construct() {
        $this->usu_codigo = null;
    }

    function getJsonData() {
        $var = get_object_vars($this);
        foreach ($var as &$value) {
            if (is_object($value) && method_exists($value, 'getJsonData')) {
                $value = $value->getJsonData();
            }
        }
        return $var;
    }
}
