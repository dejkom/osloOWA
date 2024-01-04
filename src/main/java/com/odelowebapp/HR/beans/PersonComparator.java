/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author dematjasic
 */
public class PersonComparator implements Comparator<VCodeksUsersAktualniZaposleni> {

    private List<HRCourseAttendance> invitedPersons;

    public PersonComparator(List<HRCourseAttendance> invitedPersons) {
        this.invitedPersons = invitedPersons;
    }

    @Override
    public int compare(VCodeksUsersAktualniZaposleni o1, VCodeksUsersAktualniZaposleni o2) {
        // Check if both persons are invited
        boolean isPerson1Invited = isInvited(o1);
        boolean isPerson2Invited = isInvited(o2);

        // Put invited persons on top
        if (isPerson1Invited && !isPerson2Invited) {
            //System.out.println("Compare vraca -1");
            return -1;
        } else if (!isPerson1Invited && isPerson2Invited) {
            //System.out.println("Compare vraca 1");
            return 1;
        }

        // Keep the original order for non-invited persons
        //System.out.println("Compare vraca 0");
        return 0;
    }

    public boolean isInvited(VCodeksUsersAktualniZaposleni person) {
        for (HRCourseAttendance invitedPerson : invitedPersons) {
            if (invitedPerson.getCodeksID() == person.getId()) {
                return true;
            }
        }
        return false;
    }

}
