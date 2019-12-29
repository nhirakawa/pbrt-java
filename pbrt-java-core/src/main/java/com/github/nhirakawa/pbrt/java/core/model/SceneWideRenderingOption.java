package com.github.nhirakawa.pbrt.java.core.model;

import org.derive4j.Data;

import com.github.nhirakawa.pbrt.java.core.model.camera.CameraAdt;
import com.github.nhirakawa.pbrt.java.core.model.film.AbstractImageFilm;
import com.github.nhirakawa.pbrt.java.core.model.integrator.IntegratorAdt;
import com.github.nhirakawa.pbrt.java.core.model.sampler.SamplerAdt;
import com.github.nhirakawa.pbrt.java.core.model.transform.AbstractLookAt;

@Data
public abstract class SceneWideRenderingOption {

  interface Cases<R> {

    R LOOK_AT(AbstractLookAt lookAt);
    R CAMERA(CameraAdt camera);
    R SAMPLER(SamplerAdt sampler);
    R INTEGRATOR(IntegratorAdt ingerator);
    R FILM(AbstractImageFilm imageFilm);

  }

  public abstract <R> R match(Cases<R> cases);

}
