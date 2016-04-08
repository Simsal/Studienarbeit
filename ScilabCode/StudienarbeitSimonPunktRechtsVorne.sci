clear;
clc;

//Definition der Konstanten
n = 1; //[Umdrehungen/s]
r_Reifen = 0.0275; //[m]
v_0 = n*2*r_Reifen*%pi; //[m/s] Fahrtgeschwindigkeit

alpha = 58; //[°] Winkel zwischen eingelenktem Reifen und Parallele zum Auto, ergo "Lenkwinkel"

l = 0.225; //[m] Länge des Autos
omega = v_0*cosd(alpha)/l; //[1/s] Winkelgeschwindigkeit für die Kreisbewegung des Autos

b = 0.195; //[m] Breite des Autos
A = 0.06; //[m] Abstand zum nächsten Auto
d = sqrt(0.25 * b^2 + l^2); //Diagonale vorne Rechts zu hinten zentral
phi = tan(0.5 * b / l); // Winkel an jedem berechneten Punkt -> siehe Zeichnung

t_0 = 0;
t_max = 4;
Delta_t = 0.1;
t = [t_0:Delta_t:t_max]; //[s] Zu untersuchende Zeitpunkte von 0s bis 4s in 0,1s-Schritten

//Definition der Funktion zur Berechnung des Einparkens
function xdot=f(t,x,x_d,zeitdiskret)
    global k realteil1 imaginaerteil1 realteil2 imaginaerteil2 realteil1AmWendepunkt imaginaerteil1AmWendepunkt m delta_y delta_x 
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
            
            m = sin(omega * t(k)) / cos(omega * t(k));
            
            delta_y = d * sin(m - phi);
            delta_x = d * cos(m - phi); 
            
            xdot(3) = realteil1 - delta_x;
            xdot(4) = imaginaerteil1 - delta_y;
            xdot(5) = m;
            
        else
            
            realteil2 = v_0/omega*cos(-omega*t(k)+%pi) + 2*realteil1AmWendepunkt; 
            imaginaerteil2 =  v_0/omega + v_0/omega*sin(-omega*t(k)+%pi) - 2*(v_0/omega - imaginaerteil1AmWendepunkt) ; 
            xdot(1) = realteil2;
            xdot(2) = imaginaerteil2;
            
            m = (sin(-omega*t(k)+(3*%pi/2)) / cos(-omega*t(k)+(3*%pi/2)));
            
            delta_y = d * sin(m - phi);
            delta_x = d * cos(m - phi);
            
            xdot(3) = realteil2 - delta_x;
            xdot(4) = imaginaerteil2 - delta_y;
            xdot(5) = m;
            
        end
        
        k = k+1;
    end
endfunction

//Aufruf der Funktion zur Berechnung des Einparkens
x_0 = [0;0;0;0;0;0];
x = odedc(x_0,5,Delta_t,t_0,t,f);

//Graphische Darstellung
figure(0);
clf();
xgrid(1);
plot2d(x(2,:),x(3,:));
plot(0.1,0.1575,'o');
plot2d(x(4,:),x(5,:));
//plot2d(t,x(6,:),2);
