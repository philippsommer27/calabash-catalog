# calabash-catalog
This repository servers as a catalog for experiments, results, and files related to the analysis of green software patterns within the Calabash framework.

# Results
The following two tables present an overview of results:


[Pattern catalog with results of energy experiments.](/docs/pattern_catalog.md)


[Static analysis results of grading 1,000 enterprise Java projects](/docs/projects_analysis.md)

# Experiments

### Docker

**Build**
```
docker build --platform linux/amd64 -t <user>/<tag> . 
```

**Push**
```
docker push <user>/<tag>
```

### Naming Convention
ce - <experiment_identifier> - <variation_identifier>
