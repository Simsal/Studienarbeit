clear;
clc;

//Definition der Konstanten
n = 1; //[Umdrehungen/s]
r_Reifen = 0.0275; //[m]
v_0 = n*2*r_Reifen*%pi; //[m/s] Fahrtgeschwindigkeit

alpha = 45; //[°] Winkel zwischen eingelenktem Reifen und Parallele zum Auto, ergo "Lenkwinkel"
l = 0.225; //[m] Länge des Autos
omega = v_0*cos(alpha)/l; //[1/s] Winkelgeschwindigkeit für die Kreisbewegung des Autos

b = 0.195; //[m] Breite des Autos
A = 0.06; //[m] Abstand zum nächsten Auto

t_0 = 0;
t_max = 4;
Delta_t = 0.1;
t = [t_0:Delta_t:t_max]; //[s] Zu untersuchende Zeitpunkte von 0s bis 4s in 0,1s-Schritten

//Definition der Funktion zur Berechnung des Einparkens
function xdot=f(t,x,x_d,zeitdiskret)
    global k realteil1 imaginaerteil1 realteil2 imaginaerteil2 realteil1AmWendepunkt imaginaerteil1AmWendepunkt
    k = 1;
    if zeitdiskret == 0 then
        xdot(1) = 0;
        
    else 
        if x_d(2) < ((A+b)/2) then
            realteil1 = v_0/omega*cos(omega*t(k)-%pi/2);
            imaginaerteil1 =  v_0/omega + v_0/omega*sin(omega*t(k)-%pi/2);
            xdot(1) = realteil1;
            xdot(2) = imaginaerteil1;
           
            realteil1AmWendepunkt = realteil1;
            imaginaerteil1AmWendepunkt = imaginaerteil1;
            
        else
            
            realteil2 = v_0/omega*cos(-omega*t(k)+%pi) + 2*realteil1AmWendepunkt; 
            imaginaerteil2 =  v_0/omega + v_0/omega*sin(-omega*t(k)+%pi) - 2*(v_0/omega - imaginaerteil1AmWendepunkt) ; 
            xdot(1) = realteil2;
            xdot(2) = imaginaerteil2;
            
        end
        
        k = k+1;
    end
endfunction

//Aufruf der Funktion zur Berechnung des Einparkens
x_0 = [0;0;0];
x = odedc(x_0,2,Delta_t,t_0,t,f);

//Graphische Darstellung
figure(0);
clf();
xgrid(1);
plot2d(x(2,:),x(3,:));
