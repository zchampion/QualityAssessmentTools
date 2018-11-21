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
The main goal of this extension is to provide [BlueJ](https://bluej.org/) users the ability to check their code with code quality assessment tools. At the current stage, the QualityAssessmentTools extension provides Checkstyle and PMD, which helps BlueJ users write Java code that complies to a coding standards. This project is a continuation of [Checkstyle4BlueJ extension](https://github.com/MetroCS/checkstyle4bluej).

#### Requirements
* [BlueJ](https://bluej.org/) 4.1.X running on JDK 8 or higher.
* [PMD](https://sourceforge.net/projects/pmd/files/pmd/) latest version.

#### Downloads
The latest version of the QualityAssessmentTools can be found [on the GitHub repository](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools)
under checkstyle-extension-5.4.1.jar.

#### Installation Instructions
* Download the latest [checkstyle-extension-5.4.1.jar](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/blob/master/checkstyle-extension-5.4.1.jar)
* Place the jar file in BlueJ's extension directory.
  * For Windows or Unix:
  * Navigate to a BlueJ directory
  * While in the directory, place checkstyle-extension-5.4.1.jar in lib/extensions
* For Mac OSX:
  * Control+click BlueJ.app and choose Show Package Contents
  * Navigate to Contents/Resources/Java/extensions/
  * Place checkstyle-extension-5.4.1.jar in current folder
* **NOTE:** If you are upgrading from previous version of QualityExtension or have any Checkstyle extensions active, remove your current checkstyle-extension-5.4.1.jar and any equivalents from the extension directory.

#### How to use
* In BlueJ, select **Quality Assessment Tool** from the **Tools** menu
* Select **CheckStyle** from drop down menu
* Select **PMD** from the drop down menu 
<br /> **NOTE:** The PMD tool is currently under development. Options to include a pathway to a user specific directory will be included in the next sprint. PMD will throw a 'file not found' error until the preference options are implemented. Currently, MAC OS is supported and working. If you decide you want to add the path yourself to [PMDAction.java](https://github.com/SoftwareExtensionRenovators/QualityAssessmentTools/blob/master/src/checkstyle/com/puppycrawl/tools/checkstyle/plugins/bluej/PMDAction.java) line 38 (has to include the path to the location where your PMD is installed) you would be able to use PMD.

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
