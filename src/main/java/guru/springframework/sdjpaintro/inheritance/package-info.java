package guru.springframework.sdjpaintro.inheritance;

//1. @MappedSuperClass vs. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS): 

// Class annotated with @MappedSuperClass does not have a separate table for it. The class annotated with 
// @MappedSuperClass can not be an @Entity, as it can not have a separate table for it as written in previous sentence.
//
// Class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), can be an @Entity or may not be.
// a) If a class is an @Entity and is not abstract, it has a separate table for it. 
// b) If a class is an @Entity and is abstract, it does not have a separate table for it -> the situation then is 
//    similar to the @MappedSuperClass, but the source of id's for children classes is the `hibernate_sequences` 
//    table.  
// b) If a class is not an @Entity and is abstract, it does not have a separate table for it -> the situation in db then
//    is the same as in @MappedSuperClass example.
//
// When using @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), we can not use 
// @GeneratedValue(strategy = GenerationType.IDENTITY) on a parent @Entity class. We have to use 
// @GeneratedValue(strategy = GenerationType.TABLE).
// That is why when base class is @Entity, Hibernate creates a `hibernate_sequences` table which serves as id 
// source for the @Entity classes that are children of base @Entity. 


// more here: https://gandrille.github.io/tech-notes/Languages/Java/API/JPA_(Hibernate)/Hibernate_resources/2016%20Inheritance%20strategies%20with%20JPA%20and%20Hibernate.pdf
