# QualityAssessmentTools

## QualityAssessmentTools Extension for BlueJ

### Authors
* Egor Muscat | emuscat1@msudenver.edu
* Mark Huntington |
* Jackie Nugent | jnugent3@msudenver.edu
* Zachary Champion | zchampio@msudenver.edu

### Acknowledgement
* QualityAssessmentTools code is based on Checkstyle4BlueJ extension which can be found at https://github.com/MetroCS/checkstyle4bluej

* Reference: pmd-bluej extension which can be found at https://github.com/pmd/pmd-bluej

### Usage Instructions

#### Overview
The main goal of this extension is to provide [BlueJ](https://bluej.org/) users the ability to check their code with code quality assessment tools. At the current stage, the QualityAssessmentTools extension provides Checkstyle and PMD, which helps BlueJ users write Java code that complies to a coding standards. This project is to provide an extension that can easily incorporate additional code quality tool extensions.

#### Requirements
* [BlueJ](https://bluej.org/) 4.1.X running on JDK 8 or higher.
* [PMD](https://sourceforge.net/projects/pmd/files/pmd/) latest version.

#### Downloads
The latest version of the QualityAssessmentTools can be found [on the GitHub repository](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools)
under QualityAssessmentTools-0.4.2.jar.

#### Installation Instructions
* Download the latest [QualityAssessmentTools-0.4.2.jar](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/blob/master/QualityAssessmentTools-0.4.2.jar)
* Place the jar file in BlueJ's extension directory.
  * For Windows or Unix:
  * Navigate to a BlueJ directory
  * While in the directory, place QualityAssessmentTools-0.4.2.jar in lib/extensions
* For Mac OSX:
  * Control+click BlueJ.app and choose Show Package Contents
  * Navigate to Contents/Resources/Java/extensions/
  * Place QualityAssessmentTools-0.4.2.jar in current folder
* **NOTE:** If you are upgrading from previous version of Quality Assessment Extension or have any Checkstyle extensions active, remove your current checkstyle-extension-5.4.1.jar and any equivalents from the extension directory.

#### How to use
* In BlueJ, select **Quality Assessment Tool** from the **Tools** menu
* Select **CheckStyle** from drop down menu
* To use PMD, download the current PMD update [here](https://sourceforge.net/projects/pmd/files/pmd/6.9.0). This version of this extension does not include PMD so you must install PMD under your operating system of choice. First step is to install PMD. You can install PMD from a prebuilt file or download it and build it yourself. Remember the path under where you have extracted the zip file, e.g. under Linux you could install it under ~/pmd-bin-6.9.0/. Under Windows it is usually most convenient to install PMD in the root so as to avoid spaces in directory names, (ie: Program Files) or PMD might not execute properly; the installation path could be c:\pmd-bin-6.9.0\.

  After installing the Quality Assessment Extension in BlueJ, go to "Tools / Preferences / Extensions". Select or enter the    "Path to PMD installation" that you have remembered from step one above. You can also fine-tune the "PMD Options"; by default, the rulesets "java-quickstart" are executed.

  Please see the PMD documentation for the rulesets available.


#### Documents
* [Product backlog](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/blob/master/documents/Backlog.md)
* Product's [sprint](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/blob/master/documents/CurrentSprint.md) for the current delivery
* [Life Cycle Process](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/blob/master/documents/LifeCycleProcess.md) that our team is using to develop the product

#### Licensing

This software is licensed under the terms in the file named "LICENSE" in this
directory.

The software uses the ANTLR package (http://www.antlr.org). Its license terms
are in the file named "RIGHTS.antlr" in this directory.

The software uses the Jakarta Regexp package
(http://jakarta.apache.org/regexp) and several packages from the
Jakarta Commons project (http://jakarta.apache.org/commons).
The license terms of these packages are in the file named
"LICENSE.apache" in this directory.
