package rodro.controlador;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rodro.modelo.EmitirRecibosModel;
import rodro.modelo.ReciboDto;
import rodro.util.FileUtil;

public class EmitirRecibosControler {
	
	private List<ReciboDto> recibos  = new ArrayList<ReciboDto>(EmitirRecibosModel.getRecibos());
	
	@SuppressWarnings("deprecation")
	public boolean validarRecibos() {
		List<ReciboDto> recibosEsteA�o  =  new ArrayList<ReciboDto>();
		Date d = new Date(LocalDate.now().getYear() - 1900,1,1);
		Date d2 = new Date(LocalDate.now().getYear() - 1900,12,31);
		for(ReciboDto r : recibos) {
			if( r.getEmision().after(d) && r.getEmision().before(d2) ) {
				recibosEsteA�o.add(r);
			}
		}
		if(recibosEsteA�o.size()>0) {
			cargarRecibo(recibosEsteA�o);
			return true;
		}
		return false;
		
		
	}

	private void cargarRecibo(List<ReciboDto> recibosEsteA�o) {
		Date d = new Date(LocalDate.now().getYear() - 1900);
		FileUtil.saveToFile("recibos "+ d, recibosEsteA�o);
		
	}

}
