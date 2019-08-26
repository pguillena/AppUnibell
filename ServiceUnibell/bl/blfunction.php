<?php
    function sw($r) {
        return "Prueba : ".$r;
    }
    
    function to(){
        $r=$f="";
        for($o=0;$o<=9;$o++)$r.=$o;
        for($i=65;$i<=90;$i++)
                $f.=chr($i);
        return $r.$f.strtolower($f)." ";
    }

    function ma($lt="",$md=""){
        try{
                $cz=0;$kt=1;$tn="";
                $ps=$cz;
                $nc=$cz;
                $vi=$cz;
                $tn=to();
                $ps=strpos($tn,$lt)+$kt;$vi=(strpos($tn,$md)+$kt);$nc=($vi-$ps);
                if(($nc<$cz)){
                        $nc=(strlen($tn)+$nc);
                }
                return $nc;
        }
        catch(Exception$r){
                return sw($r);
        }
        return 0;
    }

    function me($la="",$ue=""){
        try{
        $cz=0;$kt=1;$ce="";
        $co=$cz;
        $in=$cz;
        $ce=to();
        $co=strpos($ce,$la)+$kt;
        $in=($co+$ue);
        if(($in>strlen($ce))){
            $in=($in-strlen($ce));
        }
            if(($in<=$cz)){
                    $in=(strlen($ce)+$in);
            }
            return substr($ce,($in-$kt),$kt);
        }
        catch(Exception$r){
                return sw($r);
        }return "";
    }

		function dr($pt=""){
			try{$cz=0;$kt=1;
				$md=2;$ah=31;
				$lj=$cz;$kl=$cz;
				$vd="";$gw="";
				$qa="";$zf="";
				$gw=substr($pt,$cz,$kt);$lj=ma($gw,substr($pt,$ah,$kt));
				$qa=substr($pt,$cz,$ah);
				$kl=$lj;
				if(($lj%$md)==$cz){
					$kl=($lj+$kt);
				}
				for($i=$kt;$i<=$kl;$i++){
					$zf="";
					for($j=$kt;$j<=$ah;$j++){
						$vd=substr($qa,($ah-$j),$kt);
						if(($i%$md)==$cz){$vd=me($vd,-($j+$lj));
						}
						else {
							$vd=me($vd,($j+$lj));}$zf=($zf.$vd);}$qa=$zf;}$qa=substr($zf,$cz,$lj);
							return $qa;
						}
			catch(Exception$r){
				return sw($r);
			}
			return "";
		}
		
function desencriptar($pt=""){
	return dr($pt);}
?>