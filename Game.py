import pygame
import time

from pygame import mixer
from pygame.locals import*
from time import sleep

class Sprite():
    def __init__(self, x, y, w, h):
        self.x = x
        self.y = y
        self.h = h
        self.w = w
        
    def isPipe(self):
        return False
 
    def isMario(self):
        return False
    
    def isGoomba(self):
        return False
    
    def isFireball(self):
        return False
    
    def isGoombaFire(self):
        return False
    
    def drawSelf(self, screen, scrollPos):
        pass
    
    def update(self):
        pass
    
    def checkCollision(self, spriteOne, spriteTwo):
        self.spriteOneTop = spriteOne.y
        self.spriteOneBottom = spriteOne.y + spriteOne.h
        self.spriteOneLeft = spriteOne.x
        self.spriteOneRight = spriteOne.x + spriteOne.w
        
        self.spriteTwoTop = spriteTwo.y
        self.spriteTwoBottom = spriteTwo.y + spriteTwo.h
        self.spriteTwoLeft = spriteTwo.x
        self.spriteTwoRight = spriteTwo.x + spriteTwo.w
        
        if((self.spriteOneRight < self.spriteTwoLeft) 
           or (self.spriteOneLeft > self.spriteTwoRight) 
           or (self.spriteOneBottom < self.spriteTwoTop) 
           or (self.spriteOneTop > self.spriteTwoBottom)):
            return False
        
        else:
            return True
    
class Mario(Sprite):
    def __init__(self, x, y, w, h):
        super().__init__(x, y, w, h)
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.prevX = x
        self.prevY = y
        self.velocityVertical = 10
        self.currentImage = 0
        self.rightFacing = True
        self.imageMario = []
        self.jumpTime = 0
        
        for i in range(5):
            self.imageMario.append(pygame.image.load("mario{}.png".format(i + 1)))
        
    def isMario(self):
        return True
    
    def update(self):
        self.velocityVertical += 10
        self.y += self.velocityVertical
        
        self.jumpTime = self.jumpTime + 1
        
        if(self.y > 450 - self.h):
            self.velocityVertical = 0
            self.jumpTime = 0
            self.y = 450 - self.h
            
    def changeImageState(self):
        self.currentImage = self.currentImage + 1
        if(self.currentImage > 4):
            self.currentImage = 0
            
    def drawSelf(self, screen, scrollPos):
        if(self.rightFacing == True):
            screen.blit(self.imageMario[self.currentImage], (self.x - scrollPos, self.y, self.w, self.h))
            
        elif(self.rightFacing == False):
            screen.blit(pygame.transform.flip(self.imageMario[self.currentImage], True, False), (self.x - scrollPos, self.y, self.w, self.h))
        
    def removeFromPipe(self, sprite):
        # Coming from left moving right
        if(((self.x + self.w) >= sprite.x) and ((self.prevX + self.w) <= sprite.x)):
            self.x = sprite.x - self.w
        
        # Coming from right moving left
        if((self.x <= (sprite.x + sprite.w)) and (self.prevX >= (sprite.x + sprite.w))):
            self.x = sprite.x + sprite.w
            
        # Coming from above moving down
        if(((self.y + self.h) >= sprite.y) and ((self.prevY + self.h) <= sprite.y)):
            self.y = sprite.y - self.h
        
        # Coming from below moving up
        if((self.y <= (sprite.y + sprite.h)) and (self.prevY >= (sprite.y + sprite.h))):
            self.y = (sprite.y + sprite.h)
            self.velocityVertical += 10
            self.y += self.velocityVertical
            
    def setPreviousPosition(self):
        self.prevX = self.x
        self.prevY = self.y
        
class GoombaFire(Sprite):
    def __init__(self, x, y, w, h):
        super().__init__(x, y, w, h)
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.prevX = x
        self.prevY = y
        self.velocityVertical = 10
        self.velocityHorizontal = 3
        self.currentImage = 0
        self.imageGoombaFire = pygame.image.load("goomba_fire.png")
        self.selfDestruct = False
        self.timer = 0
                
    def isGoombaFire(self):
        return True
    
    def update(self):
        self.velocityVertical += 10
        self.y += self.velocityVertical
        self.x += self.velocityHorizontal
        self.timer += 1
                
        if(self.y > 450 - self.h):
            self.velocityVertical = 0
            self.y = 450 - self.h
            
        if(self.timer > 25):
            self.timer = 0
            
    def drawSelf(self, screen, scrollPos):
        screen.blit(self.imageGoombaFire, (self.x - scrollPos, self.y, self.w, self.h))
        
    def removeFromPipe(self, sprite):
        # Coming from left moving right
        if(((self.x + self.w) >= sprite.x) and ((self.prevX + self.w) <= sprite.x)):
            self.x = sprite.x - self.w
            self.velocityHorizontal = -3
        
        # Coming from right moving left
        if((self.x <= (sprite.x + sprite.w)) and (self.prevX >= (sprite.x + sprite.w))):
            self.x = sprite.x + sprite.w
            self.velocityHorizontal = 3
            
        # Coming from above moving down
        if(((self.y + self.h) >= sprite.y) and ((self.prevY + self.h) <= sprite.y)):
            self.y = sprite.y - self.h
        
        # Coming from below moving up
        if((self.y <= (sprite.y + sprite.h)) and (self.prevY >= (sprite.y + sprite.h))):
            self.y = (sprite.y + sprite.h)
            self.velocityVertical += 10
            self.y += self.velocityVertical
            
    def setPreviousPosition(self):
        self.prevX = self.x
        self.prevY = self.y
    
class Goomba(Sprite):
    def __init__(self, x, y, w, h):
        super().__init__(x, y, w, h)
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.prevX = x
        self.prevY = y
        self.velocityVertical = 10
        self.velocityHorizontal = 3
        self.currentImage = 0
        self.imageGoomba = []
        self.isOnFire = False
        
        self.imageGoomba.append(pygame.image.load("Goomba1.png"))
        self.imageGoomba.append(pygame.image.load("Goomba2.png"))
        
    def isGoomba(self):
        return True
    
    def update(self):
        self.velocityVertical += 10
        self.y += self.velocityVertical
        self.x += self.velocityHorizontal
        self.changeImageState()
                
        if(self.y > 450 - self.h):
            self.velocityVertical = 0
            self.y = 450 - self.h
            
    def changeImageState(self):
        self.currentImage = self.currentImage + 1
        if(self.currentImage > 1):
            self.currentImage = 0
            
    def drawSelf(self, screen, scrollPos):
        screen.blit(self.imageGoomba[self.currentImage], (self.x - scrollPos, self.y, self.w, self.h))
        
    def removeFromPipe(self, sprite):
        # Coming from left moving right
        if(((self.x + self.w) >= sprite.x) and ((self.prevX + self.w) <= sprite.x)):
            self.x = sprite.x - self.w
            self.velocityHorizontal = -3
        
        # Coming from right moving left
        if((self.x <= (sprite.x + sprite.w)) and (self.prevX >= (sprite.x + sprite.w))):
            self.x = sprite.x + sprite.w
            self.velocityHorizontal = 3
            
        # Coming from above moving down
        if(((self.y + self.h) >= sprite.y) and ((self.prevY + self.h) <= sprite.y)):
            self.y = sprite.y - self.h
        
        # Coming from below moving up
        if((self.y <= (sprite.y + sprite.h)) and (self.prevY >= (sprite.y + sprite.h))):
            self.y = (sprite.y + sprite.h)
            self.velocityVertical += 10
            self.y += self.velocityVertical
            
    def setPreviousPosition(self):
        self.prevX = self.x
        self.prevY = self.y
    
class Pipe(Sprite):
    def __init__(self, x, y, w, h):
        super().__init__(x, y, w, h)
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.imagePipe = pygame.image.load("pipe.png")
        
    def drawSelf(self, screen, scrollPos):
        screen.blit(self.imagePipe, (self.x - scrollPos, self.y))
        
    def isPipe(self):
        return True
        
class Fireball(Sprite):
    def __init__(self, x, y, w, h):
        super().__init__(x, y, w, h)
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.imageFireball = pygame.image.load("fireball.png")
        self.velocityVertical = 10
        self.velocityHorizontal = 10
        self.timer = 0
        
    def drawSelf(self, screen, scrollPos):
        screen.blit(self.imageFireball, (self.x - scrollPos, self.y, self.w, self.h))
        
    def isFireball(self):
        return True
    
    def update(self):
        self.y += self.velocityVertical
        self.x += self.velocityHorizontal
        self.timer += 1
                
        if(self.y > 450 - self.h):
            self.velocityVertical = -10

        if(self.y < 400 - self.h):
            self.velocityVertical = 10
            
        if(self.timer > 25):
            self.timer = 0

class Model():
    def __init__(self):
        self.sprites = []
        self.mario = Mario(50, 200, 60, 95)
        self.pipe0 = Pipe(560, 278, 55, 400)
        self.pipe1 = Pipe(150, 328, 55, 400)
        self.pipe2 = Pipe(-40, 296, 55, 400)
        self.pipe3 = Pipe(440, 280, 55, 400)
        self.pipe4 = Pipe(500, 200, 55, 400)
        self.goomba0 = Goomba(110, 368, 32, 32)
        self.goomba1 = Goomba(115, 368, 32, 32)
        self.goomba2 = Goomba(210, 368, 32, 32)
        self.goomba3 = Goomba(215, 368, 32, 32)
        self.goomba4 = Goomba(230, 368, 32, 32)
        self.goombaFireTimer = 0
        
        self.sprites.append(self.mario)
        self.sprites.append(self.pipe0)
        self.sprites.append(self.pipe1)
        self.sprites.append(self.pipe2)
        self.sprites.append(self.pipe3)
        self.sprites.append(self.pipe4)
        self.sprites.append(self.goomba0)
        self.sprites.append(self.goomba1)
        self.sprites.append(self.goomba2)
        self.sprites.append(self.goomba3)
        self.sprites.append(self.goomba4)

    def update(self):
        for sprite in self.sprites:
            sprite.update()   
            
            # Mario colliding with Pipe
            if(sprite.isPipe()):
                if(self.mario.checkCollision(self.mario, sprite)):
                    self.mario.removeFromPipe(sprite)
                
            # Goomba colliding with Pipe
            if(sprite.isGoomba()):
                for i in self.sprites:
                    if(i.isPipe()):
                        if(sprite.checkCollision(sprite, i)):
                            sprite.removeFromPipe(i)
                            
            # GoombaFire colliding with Pipe
            if(sprite.isGoombaFire()):
                for i in self.sprites:
                    if(i.isPipe()):
                        if(sprite.checkCollision(sprite, i)):
                            sprite.removeFromPipe(i)
                            
            # Goomba colliding with Fireball
            if(sprite.isFireball()):
                for i in self.sprites:
                    if(i.isGoomba()):
                        if(sprite.checkCollision(sprite, i)):
                            self.goombaOnFire = GoombaFire(i.x, i.y, i.w, i.h)
                            self.sprites.append(self.goombaOnFire)
                            self.sprites.remove(i)
            
            # Check timer for Fireball
            if(sprite.isFireball()):
                if(self.fireball.timer == 25):
                    self.sprites.remove(sprite)
                    
            # Check timer for GoombaFire
            if(sprite.isGoombaFire()):
                self.goombaFireTimer += 1
                if(self.goombaFireTimer == 15):
                    self.sprites.remove(sprite)
                    self.goombaFireTimer = 0
            
    def placeFire(self, x, y):
        self.fireball = Fireball(x, y, 47, 47)
        self.sprites.append(self.fireball)
                    
class View():
    def __init__(self, model):
        screen_size = (700,500)
        self.screen = pygame.display.set_mode(screen_size, 32)
        self.model = model
        self.tmpMario = pygame.image.load("mario1.png")
        self.groundImg = pygame.image.load("marioFloor.png")
        self.scrollPos = 0

    def update(self):    
        self.screen.fill([128,255,255])        
        
        for i in self.model.sprites:
            if(i.isMario()):
                self.scrollPos = i.x - 50
        
        for sprite in self.model.sprites:
            sprite.drawSelf(self.screen, self.scrollPos)
        
        self.screen.blit(self.groundImg, (0, 450))
        self.screen.blit(self.groundImg, (300, 450))
        pygame.display.flip()
        
    def loadImage(filename):
        img = pygame.image.load(filename)
        return img

class Controller():
    def __init__(self, model):
        self.model = model
        self.keep_going = True

    def update(self):
        self.model.mario.setPreviousPosition()
        
        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    self.keep_going = False
        keys = pygame.key.get_pressed()
        if keys[K_LEFT]:
            self.model.mario.x -= 8
            self.model.mario.rightFacing = False
            self.model.mario.changeImageState()
        if keys[K_RIGHT]:
            self.model.mario.x += 8
            self.model.mario.rightFacing = True
            self.model.mario.changeImageState()
        if keys[K_SPACE]:
            if (self.model.mario.jumpTime < 5):
                self.model.mario.velocityVertical = -50
        if keys[K_UP]:
            if (self.model.mario.jumpTime < 5):
                self.model.mario.velocityVertical = -50
        if keys[K_LCTRL] or keys[K_RCTRL]:
            self.model.placeFire(self.model.mario.x, self.model.mario.y)

mixer.init()
mixer.music.load("marioSound.mp3")
mixer.music.set_volume(0.7)
mixer.music.play()

print("Use the arrow keys to move. Press Esc to quit.")
pygame.init()
m = Model()
c = Controller(m)
v = View(m)
while c.keep_going:
    c.update()
    m.update()
    v.update()
    sleep(0.04)
print("Goodbye")
mixer.music.stop()