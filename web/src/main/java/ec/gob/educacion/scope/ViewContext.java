package ec.gob.educacion.scope;

import java.util.Map;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ViewContext implements Context {

	public Class<ViewScoped> getScope() {
		return ViewScoped.class;
	}

	public boolean isActive() {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object get(Contextual contextual, CreationalContext creationalContext) {
		Bean<Object> bean = (Bean<Object>) contextual;
		Map<String, Object> viewMap = getViewMap();
		if (viewMap.containsKey(bean.getName())) {
			return viewMap.get(bean.getName());
		} else {
			Object t = bean.create(creationalContext);
			viewMap.put(bean.getName(), t);
			return t;
		}
	}

	private Map<String, Object> getViewMap() {
		FacesContext fctx = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = fctx.getViewRoot();
		return viewRoot.getViewMap(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object get(Contextual contextual) {
		Bean<Object> bean = (Bean<Object>) contextual;
		Map<String, Object> viewMap = getViewMap();
		if (viewMap.containsKey(bean.getName())) {
			return viewMap.get(bean.getName());
		} else {
			return null;
		}
	}
}
