FROM ubuntu:22.04

RUN apt update
RUN apt install git -y
RUN cd
RUN git clone https://github.com/Roni1993/.dotfiles
RUN cd .dotfiles
RUN ./install

RUN chsh -s /usr/bin/zsh
RUN zsh
RUN zi update
