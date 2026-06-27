package AtmSystemManagementProject.main;

import AtmSystemManagementProject.controller.ATMController;

public class AtmApplication {

     public static void main(String[] args) {

        ATMController controller =
                new ATMController();

        controller.start();
    }
}
