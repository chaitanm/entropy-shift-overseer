package com.entropyshift.overseer.crypto.ellipticalcurve;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.List;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class EllipticalCurveKeyInformationDao implements IEllipticalCurveKeyInformationDao
{
    private HibernateTemplate template;

    public EllipticalCurveKeyInformationDao(HibernateTemplate template)
    {
        this.template = template;
    }

    @Override
    public List<EllipticalCurveKeyInformation> getAll()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(EllipticalCurveKeyInformation.class);
        List<EllipticalCurveKeyInformation> result = (List<EllipticalCurveKeyInformation>) template.findByCriteria(criteria);
        return result;
    }
}
