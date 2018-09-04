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
numberArrayLiteral
  : NUMBER # singleValue
  | '[' NUMBER ']' # singleValueArray
  | '[' NUMBER NUMBER+ ']' # multipleValues
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

camera : 'Camera' cameraType parameterList ;
cameraType
  : '"environment"' # environment
  | '"orthographic"' # orthographic
  | '"perspective"' # perspective
  | '"realistic"' # realistic
  ;

sampler : 'Sampler' samplerType parameterList ;
samplerType
  : ('"02sequence"' | '"lowdiscrepancy"') # zeroTwoSequence
  | '"halton"' # halton
  | '"maxmindist"' # maxMinDist
  | '"random"' # random
  | '"sobol"' # sobol
  | '"stratified"' # stratified
  ;

integrator : 'Integrator' integratorType parameterList ;
integratorType
  : '"path"' # path
  | '"bdpt"' # bdpt
  | '"directlighting"' # directLighting
  | '"mlt"' # mlt
  | '"sppm"' # sppm
  | '"whitted"' # whitted
  ;

lightSource : 'LightSource' lightSourceType parameterList ;
lightSourceType
  : '"distant"' # distant
  | '"goniometric"' # goniometric
  | '"infinite"' # infinite
  | '"point"' # point
  | '"projection"' # projection
  | '"spot"' # spot
  ;

material : 'Material' materialType parameterList ;
materialType
  : '"disney"' # disney
  | '"fourier"' # fourier
  | '"glass"' # glass
  | '"hair"' # hair
  | '"kdsubsurface"' # kdSubSurface
  | '"matte"' # matte
  | '"metal"' # metal
  | '"mirror"' # mirror
  | '"mix"' # materialMix
  | '"none"' # none
  | '"plastic"' # plastic
  | '"substrate"' # substrate
  | '"subsurface"' # subsurface
  | '"translucent"' # translucent
  | '"uber"' # uber
  ;

shape : 'Shape' '"' shapeType '"' parameterList ;
shapeType
  : 'cone' # cone
  | 'curve' # curve
  | 'cylinder' # cylinder
  | 'disk' # disk
  | 'hyperboloid' # hyperboloid
  | 'paraboloid' # paraboloid
  | 'sphere' # sphere
  | 'trianglemesh' # triangleMesh
  ;

texture : 'Texture' '"' name '"' '"' type '"' textureClass parameterList ;
textureClass
  : '"billerp"' # billerp
  | '"checkerboard"' # checkerboard
  | '"constant"' # constant
  | '"dots"' # dots
  | '"fbm"' # fbm
  | '"imagemap"' # imagemap
  | '"marble"' # marble
  | '"mix"' # textureMix
  | '"scale"' # textureScale
  | '"uv"' # uv
  | '"windy"' # windy
  | '"wrinkled"' # wrinkled
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
