grammar Pbrt;

//@header {
//	package com.github.nhirakawa.pbrt.java.parse;
//}

type
  : integer
  | floatType
  | point2
  | vector2
  | point3
  | vector3
  | normal3
  | bool
  | stringType
  | rgb
  | textureType
  ;
integer  : 'integer' ;
floatType : 'float' ;
point2 : 'point2' ;
vector2 : 'vector2' ;
point3 : 'point3' | 'point' ;
vector3 : 'vector3' | 'vector' ;
normal3 : 'normal3' | 'normal' ;
bool : 'bool' ;
stringType : 'string' ;
rgb : 'rgb' | 'color' | 'xyz' | 'spectrum' | 'blackbody' ;
textureType : 'texture' ;

boolLiteral : 'true' | 'false' ;
stringLiteral : '"' ~('"')* '"' ;
numberLiteral : NUMBER;
singleValueArray : '[' numberLiteral ']' ;
multipleValueArray : '[' numberLiteral numberLiteral+ ']' ;
numberArrayLiteral
  : numberLiteral
  | singleValueArray
  | multipleValueArray
  ;

ID : [a-zA-Z][a-zA-Z0-9]* ;
name : ID ;

value
  : boolLiteral
  | stringLiteral
  | numberArrayLiteral
  ;

parameter : '"' type name '"' value ;
parameterList : parameter* ;

x : numberArrayLiteral ;
y : numberArrayLiteral ;
z : numberArrayLiteral ;
angle : numberArrayLiteral ;

identity : 'Identity' ;
translate : 'Translate' x y z ;
scale : 'Scale' x y z ;
rotate : 'Rotate' angle x y z ;

lookAt : 'LookAt' lookAtEyeX lookAtEyeY lookAtEyeZ lookAtPointX lookAtPointY lookAtPointZ lookAtUpX lookAtUpY lookAtUpZ ;
lookAtEyeX : numberArrayLiteral ;
lookAtEyeY : numberArrayLiteral ;
lookAtEyeZ : numberArrayLiteral ;
lookAtPointX : numberArrayLiteral ;
lookAtPointY : numberArrayLiteral ;
lookAtPointZ : numberArrayLiteral ;
lookAtUpX : numberArrayLiteral ;
lookAtUpY : numberArrayLiteral ;
lookAtUpZ : numberArrayLiteral ;

coordinateSystem : 'CoordinateSystem' '"' name '"';
coordinateSystemTransform : 'CoordSysTransform' '"' name '"' ;
transform : 'Transform' numberArrayLiteral ;
concatTransform : 'ConcatTransform' numberArrayLiteral ;

m00 : numberArrayLiteral ;
m01 : numberArrayLiteral ;
m02 : numberArrayLiteral ;
m03 : numberArrayLiteral ;
m10 : numberArrayLiteral ;
m11 : numberArrayLiteral ;
m12 : numberArrayLiteral ;
m13 : numberArrayLiteral ;
m20 : numberArrayLiteral ;
m21 : numberArrayLiteral ;
m22 : numberArrayLiteral ;
m23 : numberArrayLiteral ;
m30 : numberArrayLiteral ;
m31 : numberArrayLiteral ;
m32 : numberArrayLiteral ;
m33 : numberArrayLiteral ;

transformation
  : identity
  | translate
  | scale
  | rotate
  | lookAt
  | coordinateSystem
  | coordinateSystemTransform
  | transform
  | concatTransform
  ;

film : 'Film' '"image"' parameterList ;

camera : 'Camera' '"' specificCamera ;

specificCamera
  : '"' 'environment' '"' parameterList # environmentCamera
  | '"' 'orthographic' '"' parameterList # orthographicCamera
  | '"' 'perpsective' '"' parameterList # perspectiveCamera
  | '"' 'realistic' '"' parameterList # realisticCamera
  ;

sampler : 'Sampler' specificSampler ;

specificSampler
  :  '"' ( '02sequence' | 'lowdiscrepancy' ) '"' parameterList # zeroTwoSequenceSampler
  | '"' 'halton' '"' parameterList # haltonSampler
  | '"' 'maxmindist' '"' parameterList # maxMinDistSampler
  | '"' 'random' '"' parameterList # randomSampler
  | '"' 'sobol' parameterList # sobolSampler
  | '"' 'stratified' parameterList # stratifiedSampler
  ;

integrator : 'Integrator' specificIntegrator ;

specificIntegrator
  :  '"path"' parameterList # pathIntegrator
  | '"bdpt"' parameterList # bdptIntegrator
  | '"directlighting"' parameterList # directLightingIntegrator
  | '"mlt"' parameterList # mltIntegrator
  | '"sppm"' parameterList # sppmIntegrator
  | '"whitted"' parameterList # whittedIntegrator
  ;

lightSource : 'LightSource' specificLightSourceType ;

specificLightSourceType
  :  '"distant"' parameterList # distantLightSource
  | '"goniometric"' parameterList # goniometricLightSource
  | '"infinite"' parameterList # infiniteLightSource
  | '"point"' parameterList # pointLightSource
  | '"projection"' parameterList # projectionLightSource
  | '"spot"' parameterList # spotLightSource
  ;

material : 'Material' specificMaterial ;

specificMaterial
  : '"disney"' parameterList # disneyMaterial
  | '"fourier"' parameterList # fourierMaterial
  | '"glass"' parameterList # glassMaterial
  | '"hair"' parameterList # hairMaterial
  | '"kdsubsurface"' parameterList # kdSubSurfaceMaterial
  | '"matte"' parameterList # matteMaterial
  | '"metal"' parameterList # metalMaterial
  | '"mirror"' parameterList # mirrorMaterial
  | '"mix"' parameterList # mixMaterial
  | '"none"' parameterList # noneMaterial
  | '"plastic"' parameterList # plasticMaterial
  | '"substrate"' parameterList # substrateMaterial
  | '"subsurface"' parameterList # subsurfaceMaterial
  | '"translucent"' parameterList # translucentMaterial
  | '"uber"' parameterList # uberMaterial
  ;

shape : 'Shape' specificShape ;

specificShape
  : '"cone"' parameterList # coneShape
  | '"curve"' parameterList # curveShape
  | '"cylinder"' parameterList # cylinderShape
  | '"disk"' parameterList # diskShape
  | '"hyperboloid"' parameterList # hyperboloidShape
  | '"paraboloid"' parameterList # paraboloidShape
  | '"sphere"' parameterList # sphereShape
  | '"trianglemesh"' parameterList # triangleMeshShape
  ;

texture : 'Texture' '"' name '"' '"' type '"' specificTexture ;

specificTexture
  : '"billerp"' parameterList # billerpTexture
  | '"checkerboard"' parameterList # checkerboardTexture
  | '"constant"' parameterList # constantTexture
  | '"dots"' parameterList # dotsTexture
  | '"fbm"' parameterList # fbmTexture
  | '"imagemap"' parameterList # imageMapTexture
  | '"marble"' parameterList # marbleTexture
  | '"mix"' parameterList # mixTexture
  | '"scale"' parameterList # scaleTexture
  | '"uv"' parameterList # uvTexture
  | '"windy"' parameterList # windyTexture
  | '"wrinkled"' parameterList # wrinkledTexture
  ;

include : 'Include' stringLiteral ;

attribute : 'AttributeBegin' attributeObject* 'AttributeEnd' ;
attributeObject
  : material
  | shape
  | texture
  | transformation
  ;

sceneObject
  : attribute
  | shape
  | lightSource
  | material
  | texture
  | include
  ;

world : 'WorldBegin' scene 'WorldEnd' ;
scene : sceneObject* ;

sceneWideRenderingOption
  : transformation
  | camera
  | sampler
  | integrator
  | film
  ;

pbrt : sceneWideRenderingOption* world EOF ;

// lexer rules
NUMBER
  : DIGIT*? '.' DIGIT*
  | '.' DIGIT+
  | '-' DIGIT+
  | DIGIT+
  ;

DIGIT : [0-9] ;
WS : [ \t\r\n]+ -> skip ;
COMMENT : '#' ~[\r\n]* -> skip ;
