# Introduction #

Genyris uses semantic versioning so a discussion about the significance of an initial 1.0 release makes sense.

Currently Genyris versions are all 0.x  which means backward compatibility is not guaranteed between versions. All the examples and libraries in the release itself are updated to work after languiage changes in each release. However now there are enough lines of .g application code outside of the release for incompatible changes to be annoying. I guess this means that the 0.6.x version has now sufficient features to be actually useful. So this author is keen to produce a 1.0 release and 1.x updates which can be used without breaking applications.

The implications of this are that pushing forward in experimental 'research' features will have to suspended for the 1.x release. In particular direct integration with RDF/OWL will not be available. Importing RDF/OWL is not a trivial design problem and I feel that quite a few dead ends will be explored before a comfortable solution is arrived at. In a way this a good thing because it takes the pressure off getting this right before 1.0, it would be a shame to rush out a solution that was unsatisfactory.

To meet the backward compatibility requirement, the system needs to be completely documented since the documentation defines what 1.x compatibility actually _means_. This is also a good thing since the process of producing documentation has so far uncovered design flaws and annoyances which can be corrected in the 0.x state where compatibility is not an issue.

# Release 1.0 Planned Features #

In addition to the features of 0.6.x, 1.0 we aspire to include:

  1. Complete documentation to defining the 1.x language and libraries
  1. command-line editing with (most likely) JLine **done**
  1. Jena library in the release jars with examples to form a basis of the experimentation for 2.0
  1. Windows installer **done**
  1. Debian package **done**

# Release 2.0 Planned Features #

These features are planned for 2.0 and may break backward compatibility with 1.0. They may not, but we don't know that yet.
  1. import of RDF/OWL classes allowing use of RDF/OWL as well as Genyris syntax