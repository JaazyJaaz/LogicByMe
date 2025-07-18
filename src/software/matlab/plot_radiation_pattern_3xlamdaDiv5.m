% plotting radiation pattern of 3*lambda / 5

lam = 1; % wavelength
L = 3*lam/5; % length
B = 2*pi/lam; % phase constant
t = 0:0.01:2*pi;% range theta

F = sin(t).^2; % normalized radiation intensity
E = abs((cos(B*L/2*cos(t))-cos(B*L/2))./sin(t)).^2; % far-field pattern equation 

% plotting the figure
figure()
polar(t,E)
title('Radiation Pattern of l=3\lambda/5')

% calculating the pattern solid angle
syms theta rho
ohm_function = sin(theta).^2;
ohm_int1 = int(ohm_function, theta, 0, pi);
ohm_int2 = int(ohm_int1, rho, 0, 2*pi);

fprintf('The pattern solid angle is: %s',ohm_int2)