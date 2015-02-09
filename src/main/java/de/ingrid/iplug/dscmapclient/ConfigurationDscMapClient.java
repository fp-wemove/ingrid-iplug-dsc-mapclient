/*
 * **************************************************-
 * ingrid-iplug-dsc-mapclient
 * ==================================================
 * Copyright (C) 2014 - 2015 wemove digital solutions GmbH
 * ==================================================
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * **************************************************#
 */
package de.ingrid.iplug.dscmapclient;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;

import de.ingrid.admin.IConfig;
import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.utils.PlugDescription;

@PropertiesFiles( {"config"} )
@PropertyLocations(directories = {"conf"}, fromClassLoader = true)
public class ConfigurationDscMapClient implements IConfig {
    
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(ConfigurationDscMapClient.class);
    
    @PropertyValue("plugdescription.fields")
    public String fields;
    
    @PropertyValue("plugdescription.WebmapXmlConfigFile")
    @DefaultValue("")
    public String webmapclientConfig;
    
    @Override
    public void initialize() {
    }

    @Override
    public void addPlugdescriptionValues( PlugdescriptionCommandObject pdObject ) {
        pdObject.put( "iPlugClass", "de.ingrid.iplug.dscmapclient.DscMapclientSearchPlug");
        
        // add necessary fields so iBus actually will query us
        // remove field first to prevent multiple equal entries
        pdObject.removeFromList(PlugDescription.FIELDS, "incl_meta");
        pdObject.addField("incl_meta");
        pdObject.removeFromList(PlugDescription.FIELDS, "t01_object.obj_class");
        pdObject.addField("t01_object.obj_class");
        pdObject.removeFromList(PlugDescription.FIELDS, "metaclass");
        pdObject.addField("metaclass");
        
        pdObject.put("WebmapXmlConfigFile", webmapclientConfig);
    }

    @Override
    public void setPropertiesFromPlugdescription( Properties props, PlugdescriptionCommandObject pd ) {
    	if(pd.get("WebmapXmlConfigFile") != null){
        	props.setProperty("plugdescription.WebmapXmlConfigFile", (String) pd.get("WebmapXmlConfigFile"));
    	}
    }
}
