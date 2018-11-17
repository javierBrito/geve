package ec.gob.educacion.resources;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import ec.gob.educacion.seguridades.model.Recurso;

@FacesRenderer(componentFamily="ec.gob.educacion.resources.MenuComponent", rendererType="ec.gob.educacion.resources.MenuRenderer")
public class MenuRenderer extends Renderer {
	
	@Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		final MenuComponent customComponent = (MenuComponent) component; 
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", component);
		writer.writeAttribute("id", "menu-0", null);
        writer.writeAttribute("class", "menu-0", null);
        final List<Recurso> menu = customComponent.getValue();
        writer.write(obtenerMenu(menu));
    }
	
	@Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
    }
	
	
	public String obtenerMenu(List<Recurso> menu) {
		String texto = "";
		if(menu == null || menu.isEmpty()) {
			texto += "No existe un menu asignado al usuario.";
		} else {
			for (Recurso item : menu) {
				texto += obtenerHTML(item, 1);
			}
		}
		return texto;
	}
	
	private String obtenerHTML(Recurso menu, int nivel) {
		String elemento = "";
		if(menu.getRecursos() == null || menu.getRecursos().isEmpty()) {
			elemento = crearLink(menu, nivel, false);
		} else {
			elemento = crearLink(menu, nivel, true);
			elemento += "<div class=\"menu-" + nivel  + "\">";
			for (Recurso item : menu.getRecursos()) {
				elemento += obtenerHTML(item, nivel + 1);
			}
			elemento += "</div>";
		}
		return elemento;
	}
	
	private String crearLink(Recurso menu, int nivel, boolean tieneItems) {
		String clazz = "cabecera-" + nivel;
		if(tieneItems) {
			clazz += " tiene-items";
		}
		if(nivel == 1 && !tieneItems) {
			return "";
		}
		if(menu.getRecursos() == null || menu.getRecursos().isEmpty()) {
			if(menu.getUrl() != null && menu.getUrl().indexOf("http") > 0) {
				return "<a class=\"" + clazz + "\" href=\"" + menu.getUrl() + "\">" + menu.getNombre() + "</a>";
			} else {
				return "<a class=\"" + clazz + "\" href=\"" + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/paginas/" + menu.getUrl() + "\">" + menu.getNombre() + "</a>";
			}
		}
		return "<a class=\"" + clazz + "\" href=\"\">" + menu.getNombre() + "</a>";
	}
	
}
