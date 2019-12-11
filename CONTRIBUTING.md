# Contributing to Community-Addons

## Bug Reports & Feature Requests

* [Open an issue](https://github.com/labymod/addons/issues) here on GitHub. 
If you can, **please provide a fix and create a pull request (PR) instead**; this will automatically create an issue for you. 
* Please be patient as not all items will be tested immediately - remember, LabyMod is developed by normal people like you. They have to work or go to school and LabyMod is only a product of their leisure time
* Occasionally we'll close issues if they appear stale or are too vague - please don't take this personally! 
Please feel free to re-open issues we've closed if there's something we've missed and they still need to be addressed.

## Contributing Pull Requests
PR's are even better than issues. 
We gladly accept community pull requests. 
There are a few necessary steps before we can accept a pull request:

* [Open an issue](https://github.com/labymod/addons/issues) describing the problem that you are looking to solve in 
your PR (if one is not already open), and your approach to solving it (no necessary for bug fixes - only feature contributions). 
* [Sign the CLA](https://cla-assistant.io/labymod/addons) - see also below.
* [Send a pull request](https://help.github.com/articles/using-pull-requests/) from your fork’s branch to our `master` branch.

## Workflow
In `/src/addons` all the addons are stored within a directory of the vendor.

Like this:
```
/src/addons
    ├── /labymod
    │   ├── vendor.json
    │   ├── /addon-one
    │   └── /addon-two
    └── /vendor-xy
        ├── vendor.json
        └── /example-addon
```

### Naming conventions
Vendor as well as package/addon names have to be in kabab-case (all lower case and separated with dashes).

### Minecraft versions
If you want to deploy your addon for multiple Minecraft versions this is no problem at all.

For each Minecraft version that is supported by LabyMod, this repository has a branch (e.g. `1.8.9`).<br>
The `master` branch on the other hand represents always the main version, which LabyMod is currently developed for.

An overview of the available branches and which are actively maintained and which not, is provided in the [README.md](./README.md)

### The `vendor.json` file
Every vendor needs a `vendor.json` file, to provide contact information and some personal data.
If the addons have multiple developers or you are a company/brand, simply add multiple blocks within `authors`.

The file must be in the following format:
```json
{
    "name": "labymod",
    "authors": [
       {
           "name": "LabyMedia GmbH",
           "role": "Developer",
           "contact": {
               "email": "info@labymod.net",
               "twitter": "labymod",
               "discord": "Scrummer#0001"
           }
       }
   ]
}
```
The following fields are mandatory:
* **name:** (Name of the directory - company, brand or product name - in kebab-case, e.g. product or artist name)
* **authors.name:** (Personal or company/brand name)
* **authors.role:** Role of the contributor. E.g.: Maintainer, Developer, Contributor, Translator, etc.
* **authors.contact:** Provide **at least one** of email, twitter or discord

## Contributor License Agreement
The following terms are used throughout this agreement:

* **You** - the person or legal entity including its affiliates asked to accept this agreement. An affiliate is any 
entity that controls or is controlled by the legal entity, or is under common control with it.

* **Project** - is an umbrella term that refers to any and all LabyMod open source projects.

* **Contribution** - any type of work that is submitted to a Project, including any modifications or additions to 
existing work.

* **Submitted** - conveyed to a Project via a pull request, commit, issue, or any form of electronic, written, or 
verbal communication with LabyMod, contributors or maintainers.

#### 1. Grant of Copyright License.
Subject to the terms and conditions of this agreement, You grant to the Projects’ maintainers, contributors, users and 
to LabyMod a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license to reproduce, 
prepare derivative works of, publicly display, publicly perform, sublicense, and distribute Your contributions and such 
derivative works. Except for this license, You reserve all rights, title, and interest in your contributions.

#### 2. Grant of Patent License.
Subject to the terms and conditions of this agreement, You grant to the Projects’ maintainers, contributors, users and 
to LabyMod a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable (except as stated in this section) 
patent license to make, have made, use, offer to sell, sell, import, and otherwise transfer your contributions, where 
such license applies only to those patent claims licensable by you that are necessarily infringed by your contribution 
or by combination of your contribution with the project to which this contribution was submitted. 

If any entity institutes patent litigation - including cross-claim or counterclaim in a lawsuit - against You alleging 
that your contribution or any project it was submitted to constitutes or is responsible for direct or contributory 
patent infringement, then any patent licenses granted to that entity under this agreement shall terminate as of the 
date such litigation is filed.

#### 3. Source of Contribution.
Your contribution is either your original creation, based upon previous work that, to the best of your knowledge, is 
covered under an appropriate open source license and you have the right under that license to submit that work with 
modifications, whether created in whole or in part by you, or you have clearly identified the source of the contribution 
and any license or other restriction (like related patents, trademarks, and license agreements) of which you are 
personally aware.
