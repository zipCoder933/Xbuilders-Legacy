# To Do
~~1. replace ALL hashmaps and hashets (they are a memory bottleneck)~~
1. **fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet**
   2. Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides
2. determine if OpenGl calls can be used in joml
    * it can! https://github.com/processing/processing/wiki/Advanced-OpenGL
3. change speed, cursor ray  settings
4. make block tools more intuitive
5. add dogs
6. try sorting chunks front to back?