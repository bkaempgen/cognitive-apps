BatchedFolderRegistration:

Beispiel-Aufruf:
mitkBrainStrippingMiniApps.exe BatchedFolderRegistration -o C:/Users/phiL/Desktop/Data/BatchedFolderRegistration/out/ -i C:/Users/phiL/Desktop/Data/BatchedFolderRegistration/in/ -f 20081208_T1.nrrd -m _T1.nrrd


Curl-Beispiel-Aufruf:
curl -X POST -d "@C:/Users/phiL/Desktop/request.xml" http://localhost:8080/Prototyp/SFBServletCast#i --header "Content-Type: application/rdf+xml"


curl -X POST -d "@C:/Users/phiL/Desktop/request.xml" -H "Content-Type: application/xml+rdf" -H "Accept: application/xml+rdf" -v http://localhost:8080/Prototyp/SFBServletCast#i