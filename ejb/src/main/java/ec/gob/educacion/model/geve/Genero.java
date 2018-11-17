package ec.gob.educacion.model.geve;

public enum Genero {
    M("Masculino"),
    F("Feminino");

    private String descripcion;

    private Genero(String descripcion) {
         this.descripcion = descripcion;
    }

    public String getDescripcion() {
          return descripcion;
    }
}