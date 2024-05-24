package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    if(!hasDescendant(person)){
      return "";
    }
    DescendantInfo info = findDeepestAndOldestDescendant(person, 1);
    return constructTeknonymy(person, info);
  };

    /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return Boolean which tells if a person has direct descendants  
   */
  private boolean hasDescendant(Person person){
    if (person.children() == null || person.children().length == 0){
      return false;
    }
    return true;
  }

  /**
   * Method to find the deepest and oldest descendant of a given person.
   * 
   * @param person The current person being processed.
   * @param generation The current generation level.
   * @return DescendantInfo containing the generation and the oldest person at the deepest level.
   */
  private DescendantInfo findDeepestAndOldestDescendant(Person person, int generation) {
    // Base case: if the person has no children, return their info.
    if (person.children() == null || person.children().length == 0) {
      return new DescendantInfo(generation, person);
    }

    DescendantInfo deepestOldest = null;

    // Recursively process each child.
    for (Person child : person.children()) {
      DescendantInfo childInfo = findDeepestAndOldestDescendant(child, generation + 1);

      // Update the deepestOldest if this child is deeper or older in the same generation.
      if (deepestOldest == null || childInfo.generation > deepestOldest.generation ||
          (childInfo.generation == deepestOldest.generation && childInfo.person.dateOfBirth().isBefore(deepestOldest.person.dateOfBirth()))) {
        deepestOldest = childInfo;
      }
    }

    return deepestOldest;
  }
  
/**
   * Method to construct the teknonymy string based on the person's sex and generation difference.
   * 
   * @param person The person for whom to construct the teknonymy.
   * @param info The DescendantInfo containing the deepest and oldest descendant.
   * @return The teknonymy string.
   */
  private String constructTeknonymy(Person person, DescendantInfo info) {
    int generationDifference = info.generation - 1;
    String relation;

    // Determine the base relation based on sex and generation difference.
    if (person.sex() == 'M') {
      if (generationDifference == 1) {
        relation = "father of ";
      } else {
        relation = "grandfather of ";
        for (int i = 3; i <= generationDifference; i++) {
          relation = "great-" + relation;
        }
      }
    } else {
      if (generationDifference == 1) {
        relation = "mother of ";
      } else {
        relation = "grandmother of ";
        for (int i = 3; i <= generationDifference; i++) {
          relation = "great-" + relation;
        }
      }
    }

    return relation + info.person.name();
  }


 /**
   * Helper class to store information about descendants.
   */
  private static class DescendantInfo {
    int generation;
    Person person;

    DescendantInfo(int generation, Person person) {
      this.generation = generation;
      this.person = person;
    }
  }

}
