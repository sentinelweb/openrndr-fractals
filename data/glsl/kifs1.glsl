vec2 U = c_boundsPosition.xy;
U *=3.;
U -= vec2(1.2, 1.2);
vec2 R = vec2(1., 1.); //c_boundsSize.xy;
vec2 v = (U + U - R) / R.y;
mat2 m = mat2(cos( .5 * p_ifs_rot + vec4(0, 55, 33, 0)));
for (float t = .8, i = 0.; i < 17.; i++) {
    t *= .7 + .1 * sin(2.243 * p_ifs_rot);
    v.y = abs(v *= m).y - t;
}
x_fill = vec4(.7, .9, 1, 1.) * exp(-1e4 * dot(v, v));
