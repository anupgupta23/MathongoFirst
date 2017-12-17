package com.learnacad.learnacad.Models;

import android.arch.lifecycle.ViewModel;

/**
 * Created by Sahil Malhotra on 16-09-2017.
 */

public class FiltersViewModel extends ViewModel {

   public void clearall(){
         subjectPhysicsCB = false;
         subjectMathsCB = false;
         subjectChemistryCB = false;

         categoryMainsCB = false;
         categoryAdvancedCB = false;
         categorycbseCB = false;

         classXIICB = false;
         classXICB = false;
         classXCB = false;

         diffBeginnerCB = false;
         diffIntermediateCB = false;
         diffAdvancedCB = false;

         mediumEnglishCB = false;
         mediumHindiCB = false;

   }

    public boolean subjectPhysicsCB;
    public boolean subjectMathsCB;
    public boolean subjectChemistryCB;

    public boolean categoryMainsCB;
    public boolean categoryAdvancedCB;
    public boolean categorycbseCB;

    public boolean classXIICB;
    public boolean classXICB;
    public boolean classXCB;

    public boolean diffBeginnerCB;
    public boolean diffIntermediateCB;
    public boolean diffAdvancedCB;

    public boolean mediumEnglishCB;
    public boolean mediumHindiCB;


    public void setSubjectPhysicsCB(boolean subjectPhysicsCB) {
        this.subjectPhysicsCB = subjectPhysicsCB;
    }

    public void setSubjectMathsCB(boolean subjectMathsCB) {
        this.subjectMathsCB = subjectMathsCB;
    }

    public void setSubjectChemistryCB(boolean subjectChemistryCB) {
        this.subjectChemistryCB = subjectChemistryCB;
    }

    public void setCategoryMainsCB(boolean categoryMainsCB) {
        this.categoryMainsCB = categoryMainsCB;
    }

    public void setCategoryAdvancedCB(boolean categoryAdvancedCB) {
        this.categoryAdvancedCB = categoryAdvancedCB;
    }

    public void setCategorycbseCB(boolean categorycbseCB) {
        this.categorycbseCB = categorycbseCB;
    }

    public void setClassXIICB(boolean classXIICB) {
        this.classXIICB = classXIICB;
    }

    public void setClassXICB(boolean classXICB) {
        this.classXICB = classXICB;
    }

    public void setClassXCB(boolean classXCB) {
        this.classXCB = classXCB;
    }

    public void setDiffBeginnerCB(boolean diffBeginnerCB) {
        this.diffBeginnerCB = diffBeginnerCB;
    }

    public void setDiffIntermediateCB(boolean diffIntermediateCB) {
        this.diffIntermediateCB = diffIntermediateCB;
    }

    public void setDiffAdvancedCB(boolean diffAdvancedCB) {
        this.diffAdvancedCB = diffAdvancedCB;
    }

    public void setMediumEnglishCB(boolean mediumEnglishCB) {
        this.mediumEnglishCB = mediumEnglishCB;
    }

    public void setMediumHindiCB(boolean mediumHindiCB) {
        this.mediumHindiCB = mediumHindiCB;
    }
}
