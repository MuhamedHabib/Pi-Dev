<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\File;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;


/**
 * @Route("/reclamation")
 */
class ReclamationController extends AbstractController
{
    /**
     * @Route("/", name="reclamation_index", methods={"GET"})
     */
    public function index(ReclamationRepository $reclamationRepository): Response
    {
        return $this->render('reclamation/index.html.twig', [
            'reclamations' => $reclamationRepository->findAll(),        ]);
    }

    /**

     * @Route("/new", name="reclamation_new", methods={"GET","POST"})
     */
    public function new(Request $request)
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $image=$request->files->get('reclamation')['screenshot'];
            $uploads_directory=$this->getParameter('kernel.root_dir'). '/../public/img';
            $filename=md5(uniqid()) . '.' . $image->guessExtension();
            $image->move(
                $uploads_directory,
                $filename
            );

            $reclamation->setScreenshot($filename);
            $reclamation->setStatut("en attente");
            $em = $this->getDoctrine()->getManager();
            $em->persist($reclamation);
            $em->flush();

            return $this->redirectToRoute('reclamation_show');
        }

        return $this->render('reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idReclamation}", name="reclamation_show", methods={"GET","POST"})
     */
    public function show(Reclamation $reclamation , Request $request): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('reclamation_index');
        }
        return $this->render('reclamation/show.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @Route("/triId", name="triId")
     */

    public function TriId(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $query = $em->createQuery(
            'SELECT r FROM App\Entity\Reclamation r ORDER BY Rec.date_creation'
        );


        $rep = $query->getResult();

        return $this->render('Reclamation/show.html.twig',
            array('Reclamation' => $rep));

    }

    /**
     * @Route("/Supp/{idReclamation}", name="reclamation_delete")
     */
    public function delete($idReclamation, ReclamationRepository $repository)
    {
        $reclamation=$repository->find($idReclamation);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reclamation);
            $entityManager->flush();


        return $this->redirectToRoute('reclamation_index');
    }

    /**
     * @Route("/Imprimer", name="Imprimer")
     */
    public function Imprimer()
    {
        $repository=$this->getDoctrine()->getRepository(Reclamation::class);
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $Reclamation=$repository->findAll();


        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('Reclamation/index.html.twig',
            ['Reclamation'=>$Reclamation]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Reclamation_finale.pdf", [
            "Attachment" => true
        ]);
    }
}
