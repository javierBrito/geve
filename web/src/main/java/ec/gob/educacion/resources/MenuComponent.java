package ec.gob.educacion.resources;

import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import ec.gob.educacion.seguridades.model.Recurso;

@FacesComponent("ec.gob.educacion.resources.MenuComponent")
public class MenuComponent extends UIComponentBase {
	
	private List<Recurso> value;
	
	@Override
    public String getFamily() {        
        return "ec.gob.educacion.resources.MenuComponent";
    }
 
	@Override
    public String getRendererType() {
        return "ec.gob.educacion.resources.MenuRenderer";
    }

	public List<Recurso> getValue() {
	    final ValueExpression valueExpression = getValueExpression("value");
	    if (valueExpression != null) {
	        @SuppressWarnings("unchecked")
			final List<Recurso> elValue = (List<Recurso>) valueExpression.getValue(
	            getFacesContext().getELContext());
	        return elValue;
	    }
	    return value;
	}

	public void setValue(List<Recurso> value) {
		this.value = value;
	}
    
}
