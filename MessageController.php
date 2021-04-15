<?php

namespace App\Controller;

use App\Entity\Message;
use App\Form\MessageType;
use App\Repository\MessageRepository;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/message")
 */
class MessageController extends AbstractController
{
    /**
     * @Route("/", name="message_index", methods={"GET"})
     */
    public function index(MessageRepository $messageRepository): Response
    {
        return $this->render('message/index.html.twig', [
            'messages' => $messageRepository->findAll(),
        ]);
    }

    /**
     * @Route("/{idMessage}", name="message_show", methods={"GET", "POST"})
     */
    public function show(Message $message , Request $request): Response
    {
        $form = $this->createForm(MessageType::class, $message);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('message_index');
        }
        return $this->render('message/show.html.twig', [
            'message' => $message,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/Supp/{idMessage}", name="message_delete")
     */
    public function delete($idMessage,MessageRepository $repository)
    {
        $message=$repository->find($idMessage);
        $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($message);
            $entityManager->flush();


        return $this->redirectToRoute('message_index');
    }
}
