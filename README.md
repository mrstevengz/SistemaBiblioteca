# 📚 Caso de Estudio: Sistema de Biblioteca

## 🧾 Autores  

Proyecto académico - **Sistema de Biblioteca (JPA)**  

- 👨‍💻 **Steven Sequeira**  
- 👩‍💻 **Gabriela Guerrero**  
- 👨‍💻 **Fernando Gomez**  
- 👨‍💻 **Guillermo Vega**  
- 👨‍💻 **Allan Acuña**  

---

## 🧩 Descripción del Proyecto

Una pequeña biblioteca desea registrar información sobre sus **libros y autores**, además de clasificar los libros en diferentes **categorías**.

El proyecto modela las siguientes entidades y relaciones:
### 🔹 Entidades

1. **Autor**
   - `id` (Long)
   - `nombre` (String)
   - `nacionalidad` (String)
   - `fechaNacimiento` (LocalDate)

2. **Libro**
   - `id` (Long)
   - `titulo` (String)
   - `annoPublicacion` (int)
   - `autor` (Autor)

3. **Categoria**
   - `id` (Long)
   - `nombre` (String)

---

## 🔗 Relaciones entre Entidades

| Relación | Tipo | Descripción |
|-----------|------|--------------|
| **Autor → Libro** | `@OneToMany` | Un autor puede tener varios libros. |
| **Libro → Autor** | `@ManyToOne` | Cada libro tiene un solo autor. |
| **Libro ↔ Categoria** | `@ManyToMany` | Un libro puede pertenecer a varias categorías y una categoría puede aplicarse a muchos libros. |


## 🚀 Funcionalidades Principales

- Inserción de **autores**, **libros** y **categorías** en la base de datos.
- Asociación de libros con sus autores y categorías.
- CRUD para cada entidad del proyecto.
