package guru.springframework.sdjpaintro.inheritance;

//0. @Entity class -> A class annotated with an @Entity is a class that is "querable" it means that we can use the 
//   type of this class in JPA queries for example.
//   To be honest I do not see any point of having an `abstract` class which is also an @Entity, as we can not 
//   instantiate the object of this class for the purpose of using this object in a db query. I created an example 
//   like that just for learning/educational purposes.
//
//1. @MappedSuperClass
// Class annotated with @MappedSuperClass does not have a separate table for it. The class annotated with 
// @MappedSuperClass can not be an @Entity, as it can not have a separate table for it as written in previous sentence.
//
//2. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS),
//   Class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), can be an @Entity or may not be.
//   In my opinion, @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) should be named `TABLE_PER_ENTITY` as it
//   would describe way better which classes have their own table in db.
//   Below are explanations how does this annotation work.
//
// a) If a class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) is also annotated with @Entity,
//    there is a separate table for it. Source of id's for the classes/entities in hierarchy is taken from separate 
//    `hibernate_sequences` table. That is why we have to use @GeneratedValue(strategy = GenerationType.TABLE) on id 
//    field in such class.
// b) If a class annotated with @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) is not annotated with 
//    @Entity, it does not have a separate table for it. The situation in db then is the same as in @MappedSuperClass
//    example.
//
// When using @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS), we can not use 
// @GeneratedValue(strategy = GenerationType.IDENTITY) on an id field in parent @Entity class. We have to use 
// @GeneratedValue(strategy = GenerationType.TABLE).
// That is because when base class is annotated with @Entity and 
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS),
// Hibernate creates a `hibernate_sequences` table which serves as id source for the @Entity classes in the hierarchy. 

// more here: https://gandrille.github.io/tech-notes/Languages/Java/API/JPA_(Hibernate)/Hibernate_resources/2016%20Inheritance%20strategies%20with%20JPA%20and%20Hibernate.pdf
