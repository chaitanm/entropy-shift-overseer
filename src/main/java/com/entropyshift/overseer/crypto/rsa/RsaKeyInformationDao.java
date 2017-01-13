package com.entropyshift.overseer.crypto.rsa;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateTemplate;

import java.util.List;

/**
 * Created by chaitanya.m on 1/12/17.
 */
public class RsaKeyInformationDao implements IRsaKeyInformationDao
{
    private HibernateTemplate template;

    public RsaKeyInformationDao(HibernateTemplate template)
    {
        this.template = template;
    }

    @Override
    public List<RsaKeyInformation> getAll()
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(RsaKeyInformation.class);
        List<RsaKeyInformation> result = (List<RsaKeyInformation>) template.findByCriteria(criteria);
        return result;
    }
}
