package guru.springframework.sdjpaintro.inheritance;

//1. @MappedSuperClass
// Class annotated with @MappedSuperClass does not have a separate table for it. The class annotated with 
// @MappedSuperClass can not be an @Entity, as it can not have a separate table for it as written in previous sentence.
//
//2. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS),
// Class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), can be an @Entity or may not be.
// a) If a class is an @Entity and is not abstract, it has a separate table for it. Source of id's for the 
//    children classes/entities is taken from separate `hibernate_sequences` table. That is why we have to use 
//    @GeneratedValue(strategy = GenerationType.TABLE) on id field in such class.
// b) If a class is an @Entity and is abstract, it does not have a separate table for it. Source of id's for the 
//    children classes/entities is taken from separate `hibernate_sequences` table. That is why we have to use 
//    @GeneratedValue(strategy = GenerationType.TABLE) on id field in such class. 
// b) If a class is not an @Entity and is abstract, it does not have a separate table for it -> the situation in db then
//    is the same as in @MappedSuperClass example.
//
// Summing up: If a class is `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)` and is an `@Entity`,  no 
// matter if it is abstract or not, there will be a separate table called `hibernate_sequences` that will be the 
// source of ids for the classes/entities in this class hierarchy. 
// If this class is not abstract, it will have a separate table for it.
// If this class is abstract, it will not have a separate table for it. 
//
// When using @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), we can not use 
// @GeneratedValue(strategy = GenerationType.IDENTITY) on an id field in parent @Entity class. We have to use 
// @GeneratedValue(strategy = GenerationType.TABLE).
// That is because when base class is @Entity and @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS),
// Hibernate creates a `hibernate_sequences` table which serves as id source for the @Entity classes that are 
// children of base @Entity. 

// more here: https://gandrille.github.io/tech-notes/Languages/Java/API/JPA_(Hibernate)/Hibernate_resources/2016%20Inheritance%20strategies%20with%20JPA%20and%20Hibernate.pdf
