<?xml version="1.0"?>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
	
	 <#if !appCompat && !(hasDependency('com.android.support:support-v4'))>
            <dependency mavenUrl="com.android.support:support-v4:${buildApi}.+"/>
     </#if>

    <#if appCompat && !(hasDependency('com.android.support:appcompat-v7'))>
           <dependency mavenUrl="com.android.support:appcompat-v7:${buildApi}.+" />
    </#if>

    <#if (buildApi gte 22) && appCompat && !(hasDependency('com.android.support:design'))>
        <dependency mavenUrl="com.android.support:design:${buildApi}.+" />
    </#if>

<#if generateLayout>
    <#include "../common/recipe_simple.xml.ftl" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

	<merge from="root/res/values/strings.xml.ftl"
             to="${escapeXmlAttribute(resOut)}/values/strings.xml" />
	
	<merge from="root/res/values/colors.xml.ftl"
             to="${escapeXmlAttribute(resOut)}/values/colors.xml" />
			 
	<merge from="root/build.gradle.ftl"
             to="${escapeXmlAttribute(projectOut)}/build.gradle" />
			 
	<merge from="root/settings.gradle.ftl"
             to="${escapeXmlAttribute(topOut)}/settings.gradle" />
			 
	<copy from="root/src/app_package/utils"
             to="${escapeXmlAttribute(srcOut)}/utils" />
			 
	<copy from="root/res/drawable-xhdpi"
             to="${escapeXmlAttribute(resOut)}/drawable-xhdpi" />

	<copy from="root/vito_base_lib"
             to="${escapeXmlAttribute(topOut)}/vito_base_lib" />
			 
	<instantiate from="root/assets/json/vito_home_tab_data.json.ftl"
                   to="${escapeXmlAttribute(manifestOut)}/assets/json/vito_home_tab_data.json" />
			 
	<instantiate from="root/assets/json/vito_my_info.json.ftl"
                   to="${escapeXmlAttribute(manifestOut)}/assets/json/vito_my_info.json" />
			 
    <instantiate from="root/src/app_package/SimpleActivity.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
				   
	<instantiate from="root/src/app_package/MyApplication.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/MyApplication.java" />
	
	<instantiate from="root/src/app_package/SimpleFragment.java.ftl"
        to="${escapeXmlAttribute(srcOut)}/fragment/SimpleFragment.java" />   
		
	<instantiate from="root/src/app_package/MyInfoFragment.java.ftl"
        to="${escapeXmlAttribute(srcOut)}/fragment/MyInfoFragment.java" />   

	<instantiate from="root/src/app_package/MainActivityFragment.java.ftl"
        to="${escapeXmlAttribute(srcOut)}/fragment/MainActivityFragment.java" />   
		
    <instantiate from="root/res/layout/activity_simple.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
		
	<instantiate from="root/res/layout/fg_main.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/fg_main.xml" />
		
	<instantiate from="root/res/layout/fg_my_info.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/fg_my_info.xml" />

	<open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml"/>    
	
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
</recipe>
