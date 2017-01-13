package com.entropyshift.overseer.crypto.jwt;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.List;

/**
 * Created by chaitanya.m on 1/13/17.
 */
public class JwtAppKeyMetaInformationDao implements IJwtAppKeyMetaInformationDao
{
    private HibernateTemplate template;

    public JwtAppKeyMetaInformationDao(HibernateTemplate template)
    {
        this.template = template;
    }

    @Override
    public List<JwtAppKeyMetaInformation> getAll()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(JwtAppKeyMetaInformation.class);
        List<JwtAppKeyMetaInformation> result = (List<JwtAppKeyMetaInformation>) template.findByCriteria(criteria);
        return result;
    }
}
