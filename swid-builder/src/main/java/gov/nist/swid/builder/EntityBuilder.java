/**
 * Portions of this software was developed by employees of the National Institute
 * of Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 17 United States Code Section 105, works of NIST employees are
 * not subject to copyright protection in the United States and are considered to
 * be in the public domain. Permission to freely use, copy, modify, and distribute
 * this software and its documentation without fee is hereby granted, provided that
 * this notice and disclaimer of warranty appears in all copies.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS' WITHOUT ANY WARRANTY OF ANY KIND, EITHER
 * EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY
 * THAT THE SOFTWARE WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND FREEDOM FROM
 * INFRINGEMENT, AND ANY WARRANTY THAT THE DOCUMENTATION WILL CONFORM TO THE
 * SOFTWARE, OR ANY WARRANTY THAT THE SOFTWARE WILL BE ERROR FREE. IN NO EVENT
 * SHALL NIST BE LIABLE FOR ANY DAMAGES, INCLUDING, BUT NOT LIMITED TO, DIRECT,
 * INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF, RESULTING FROM, OR
 * IN ANY WAY CONNECTED WITH THIS SOFTWARE, WHETHER OR NOT BASED UPON WARRANTY,
 * CONTRACT, TORT, OR OTHERWISE, WHETHER OR NOT INJURY WAS SUSTAINED BY PERSONS OR
 * PROPERTY OR OTHERWISE, AND WHETHER OR NOT LOSS WAS SUSTAINED FROM, OR AROSE OUT
 * OF THE RESULTS OF, OR USE OF, THE SOFTWARE OR SERVICES PROVIDED HEREUNDER.
 */

package gov.nist.swid.builder;

import static gov.nist.swid.builder.util.Util.requireNonEmpty;

import java.util.LinkedList;
import java.util.List;

public class EntityBuilder extends AbstractBuilder<EntityBuilder> {
    private String name;
    private String regid;
    private List<String> roles;
    private String thumbprint;

    protected EntityBuilder() {
        super();
    }

    @Override
    public void reset() {
        super.reset();
        name = null;
        regid = null;
        roles = new LinkedList<>();
        thumbprint = null;
    }

    public static EntityBuilder create() {
        return new EntityBuilder();
    }

    public String getName() {
        return name;
    }

    public String getRegid() {
        return (regid == null ? SWIDConstants.ENTITY_REGID_DEFAULT : regid);
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getThumbprint() {
        return thumbprint;
    }

    public EntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the to-be-built entity's regid to the provided value.
     * 
     * @param regid
     *            the regid value
     * @return the same builder instance
     */
    public EntityBuilder regid(String regid) {
        if (SWIDConstants.ENTITY_REGID_DEFAULT.equals(regid)) {
            this.regid = null;
        } else {
            this.regid = regid;
        }
        return this;
    }

    public EntityBuilder thumbprint(String thumbprint) {
        this.thumbprint = thumbprint;
        return this;
    }

    public EntityBuilder addRole(String role) {
        this.roles.add(role);
        return this;
    }

    @Override
    public boolean isValid() {
        boolean retval = (name != null && !roles.isEmpty() && roles.contains(SWIDConstants.ROLE_TAG_CREATOR));
        return retval;
    }

    @Override
    public void validate() {
        requireNonEmpty(name, "name");
        if (roles.isEmpty() || !roles.contains(SWIDConstants.ROLE_TAG_CREATOR)) {
            throw new IllegalStateException(
                    "at least the role '" + SWIDConstants.ROLE_TAG_CREATOR + "' must be provided");
        }
    }
}