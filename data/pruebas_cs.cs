// Actulizamos el Valor en la posicion que indica el index_1
            if (simbolo.getValor() instanceof LinkedList) {
                LinkedList<Object> vec = (LinkedList<Object>) simbolo.getValor();
                int idx = (Integer) valor;
                if (idx < 0 || idx >= vec.size()) {
                    return new Errores("Semántico", "Índice fuera de rango", linea, columna);
                }

                // Accede al valor del vector, para cambiarlo por el nuevo dato
                vec.set(idx, valorExpresion);
                // Accede al valor del vector.
                var value = vec.get(idx);
                // Accede al valor del dato extraido en el vector.
                this.tipo.setTipo(tipoDato.getType(value));
                this.tipo.setTipo(simbolo.getTipo().getTipo());
                return null;
            }