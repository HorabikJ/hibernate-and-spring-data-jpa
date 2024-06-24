package guru.springframework.sdjpaintro.inheritance;

//1. @MappedSuperClass vs. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS): 

// Class annotated with @MappedSuperClass does not have a separate table for it. The class annotated with 
// @MappedSuperClass can not be an @Entity, as it can not have a separate table for it as written above.
//
// Class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), can be an @Entity or may not.
// If this class is an @Entity, it has a separate table for it. If it is not an @Entity, it does not have a separate 
// table for it.
// Basically if, class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) is not an @Entity, the
// setup in db looks the same as in @MappedSuperClass example. 
// I do not know how Hibernate interprets it though when creating queries.

// more here: https://gandrille.github.io/tech-notes/Languages/Java/API/JPA_(Hibernate)/Hibernate_resources/2016%20Inheritance%20strategies%20with%20JPA%20and%20Hibernate.pdf
