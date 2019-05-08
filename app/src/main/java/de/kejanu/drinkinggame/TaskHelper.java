package de.kejanu.drinkinggame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class TaskHelper {

    public void removeTasksWhichNeedMoreNames(ArrayList<Task> taskList, int amount) {
        for (Iterator<Task> it = taskList.iterator(); it.hasNext();) {
            if (it.next().getRequiredNames() > amount) {
                it.remove();
            }
        }
    }

    private String returnGenderTextsReplaced(Person p, String text, int position) {
        HashMap<String, String> genderMap = new HashMap<>();
        genderMap.put("ihm", "ihr");
        genderMap.put("seine", "ihre");
        genderMap.put("er", "sie");
        genderMap.put("seinen", "ihren");
        genderMap.put("sein", "ihr");

        // We check for the "key" in the String and replace it, if necessary
        for (Map.Entry<String, String> entry : genderMap.entrySet()) {
            String toReplace = "{" + entry.getKey() + position + "}";
            if (text.contains(toReplace)) {
                text = text.replace(toReplace, p.getGender() == Gender.MALE ? entry.getKey() : entry.getValue());
            }
        }
        return text;
    }

    public void populateTasksWithNames(ArrayList<Task> taskList, ArrayList<Person> personList) {
        for (Task task : taskList) {
            for (int i = 0; i < task.getRequiredNames(); ++i) {
                task.setText(task.getText().replace("{name" + i + "}", personList.get(i).getName()));

                if (task.isNeedsGenderCheck()) {
                    task.setText(returnGenderTextsReplaced(personList.get(i), task.getText(), i));
                }

                // Specific Order Texts
                if (task instanceof Order) {
                    Order tmpOrder = (Order) task;
                    if (tmpOrder.getSecondTextArray() != null) {
                        for (int j = 0; j < tmpOrder.getSecondTextArray().length; ++j) {
                            tmpOrder.getSecondTextArray()[j] = tmpOrder.getSecondTextArray()[j].replace("{name" + i + "}", personList.get(i).getName());
                            if (tmpOrder.isNeedsGenderCheck()) {
                                tmpOrder.getSecondTextArray()[j] = returnGenderTextsReplaced(personList.get(i), tmpOrder.getSecondTextArray()[j], i);
                            }
                        }
                    }
                }

                // Specific Rule Texts
                if (task instanceof Rule) {
                    Rule tmpRule = (Rule) task;
                    tmpRule.setEndText(tmpRule.getEndText().replace("{name" + i + "}", personList.get(i).getName()));
                    if (tmpRule.isNeedsGenderCheck()) {
                        tmpRule.setEndText(returnGenderTextsReplaced(personList.get(i), tmpRule.getEndText(), i));
                    }
                }
            }
            Collections.shuffle(personList);
        }
    }
}
